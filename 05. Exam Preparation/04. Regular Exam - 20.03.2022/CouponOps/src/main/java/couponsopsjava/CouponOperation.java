package couponsopsjava;

import java.util.*;
import java.util.stream.Collectors;


public class CouponOperation implements ICouponOperation {
    private Map<String, Coupon> couponsByCode;

    private Map<String, Website> websiteByDomain;

    // the key is the websiteDomain, the value is a List<Coupon>
    private Map<String, List<Coupon>> couposnByWebsitesDomain;

    public CouponOperation() {
        this.couponsByCode = new LinkedHashMap<>();
        this.websiteByDomain = new LinkedHashMap<>();
        this.couposnByWebsitesDomain = new LinkedHashMap<>();
    }

    public void registerSite(Website w) {
        if (exist(w)) {
            throw new IllegalArgumentException();
        }

        this.websiteByDomain.put(w.domain, w);
        this.couposnByWebsitesDomain.put(w.domain, new ArrayList<>());
    }

    public void addCoupon(Website w, Coupon c) {
        if (!exist(w) || exist(c)) {
            throw new IllegalArgumentException();
        }

        this.couponsByCode.put(c.code, c);
        this.couposnByWebsitesDomain.get(w.domain).add(c);
    }

    public Website removeWebsite(String domain) {
        if (!this.websiteByDomain.containsKey(domain)) {
            throw new IllegalArgumentException();
        }

        Website websiteForRemoving = this.websiteByDomain.get(domain);
        this.websiteByDomain.remove(websiteForRemoving.domain);

        List<Coupon> coupounsForRemoving = this.couposnByWebsitesDomain.get(domain);
        coupounsForRemoving.forEach((coupon -> {
            this.couponsByCode.remove(coupon.code);
        }));

        coupounsForRemoving.clear();

        return websiteForRemoving;
    }

    public Coupon removeCoupon(String code) {
        if (!this.couponsByCode.containsKey(code)) {
            throw new IllegalArgumentException();
        }

        this.couponsByCode.remove(code);
        Coupon couponForRemoving = this.couponsByCode.get(code);

        this.couposnByWebsitesDomain.values().
                stream()
                .flatMap(List::stream)
                .collect(Collectors.toList())
                .remove(couponForRemoving);
        return couponForRemoving;
    }

    public boolean exist(Website w) {
        return this.websiteByDomain.containsKey(w.domain);
    }

    public boolean exist(Coupon c) {
        return this.couponsByCode.containsKey(c.code);
    }

    public Collection<Website> getSites() {
        return this.websiteByDomain.values();
    }

    public Collection<Coupon> getCouponsForWebsite(Website w) {
        return this.couposnByWebsitesDomain.get(w.domain);
    }

    public void useCoupon(Website w, Coupon c) {
        if (!exist(w) || !exist(c)) {
            throw new IllegalArgumentException();
        }

        if (!this.couposnByWebsitesDomain.get(w.domain).contains(c)) {
            throw new IllegalArgumentException();
        }

        this.couposnByWebsitesDomain.get(w.domain).remove(c);
        this.couponsByCode.remove(c.code);
    }

    public Collection<Coupon> getCouponsOrderedByValidityDescAndDiscountPercentageDesc() {
        return this.couponsByCode.values()
                .stream()
                .sorted((c1, c2) -> {
                    int result = Integer.compare(c2.validity, c1.validity);

                    if (result == 0) {
                        result = Integer.compare(c2.discountPercentage, c1.discountPercentage);
                    }
                    return result;
                })
                .collect(Collectors.toList());
    }

    public Collection<Website> getWebsitesOrderedByUserCountAndCouponsCountDesc() {
        return this.websiteByDomain.values()
                .stream()
                .sorted( (w1, w2) -> {
                      int result = Integer.compare(w1.usersCount, w2.usersCount);

                      if (result == 0) {
                          int couponsCount = this.couposnByWebsitesDomain.get(w1.domain).size();
                          int couponsCount2 = this.couposnByWebsitesDomain.get(w2.domain).size();

                          result = Integer.compare(couponsCount2, couponsCount);
                      }

                      return result;
                })
                .collect(Collectors.toList());
    }
}
