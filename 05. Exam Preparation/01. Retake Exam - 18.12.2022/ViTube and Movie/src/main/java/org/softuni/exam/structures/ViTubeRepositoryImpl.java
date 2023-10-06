package org.softuni.exam.structures;

import org.softuni.exam.entities.User;
import org.softuni.exam.entities.Video;

import java.util.*;
import java.util.stream.Collectors;

public class ViTubeRepositoryImpl implements ViTubeRepository {
    private Map<String, User> usersById;
    private Map<String, Video> videosById;

    // the key is an userId, the value is a List of watchedVideos (List<Video>)
    private Map<String, List<Video>> usersWatchedVideos;
    // the key is an userId, the value is a List of likedVideos (List<Video>)
    private Map<String, List<Video>> usersLikedVideos;
    // the key is an userId, the value is a List of dislikedVideos (List<Video>)
    private Map<String, List<Video>> usersDislikedVideos;
    // the key is usersId, the value is a user, that has never watched, liked or disliked a video
    private Map<String, User> passiveUsers;


    public ViTubeRepositoryImpl() {
        this.usersById = new LinkedHashMap<>();
        this.videosById = new LinkedHashMap<>();
        this.usersWatchedVideos = new LinkedHashMap<>();
        this.usersLikedVideos = new LinkedHashMap<>();
        this.usersDislikedVideos = new LinkedHashMap<>();
        this.passiveUsers = new LinkedHashMap<>();
    }

    @Override
    public void registerUser(User user) {
        // adding the given user to the map userById
        this.usersById.put(user.getId(), user);

        // adding the userId as key to the maps usersWatchedVideos, usersLikedVideos
        // and usersDislikedVideo. Then initialisation
        this.usersWatchedVideos.put(user.getId(), new ArrayList<>());
        this.usersLikedVideos.put(user.getId(), new ArrayList<>());
        this.usersDislikedVideos.put(user.getId(), new ArrayList<>());

        // adding the user to the map passiveUsers
        this.passiveUsers.put(user.getId(), user);
    }

    // adding the video to the map videoById
    @Override
    public void postVideo(Video video) {
        this.videosById.put(video.getId(), video);
    }

    // returns if the user is in the map usersById
    @Override
    public boolean contains(User user) {
        return this.usersById.containsKey(user.getId());
    }

    /// returns if the video is in the map videosById
    @Override
    public boolean contains(Video video) {
        return this.videosById.containsKey(video.getId());
    }

    // getting all videos (from the map videosById)
    @Override
    public Iterable<Video> getVideos() {
        return this.videosById.values();
    }

    @Override
    public void watchVideo(User user, Video video) throws IllegalArgumentException {
        // exception by condition
        if (!contains(user) || !contains(video)) {
            throw new IllegalArgumentException();
        }

        // getting the number of the views of the given video (using the map videosById)
        int watchedVideos = this.videosById.get(video.getId()).getViews();
        // incrementing the dislikes by 1 using a setter
        this.videosById.get(video.getId()).setViews(watchedVideos + 1);
        // adding the video to the map usersWatchedVideos
        this.usersWatchedVideos.get(user.getId()).add(video);

        // removing the user from the map passiveUsers (because he has watched a video)
        this.passiveUsers.remove(user.getId());
    }

    @Override
    public void likeVideo(User user, Video video) throws IllegalArgumentException {
        // exception by condition
        if (!contains(user) || !contains(video)) {
            throw new IllegalArgumentException();
        }

        // getting the number of the likes of the given video (using the map videosById)
        int likedVideos = this.videosById.get(video.getId()).getLikes();
        // incrementing the likes by 1 using a setter
        this.videosById.get(video.getId()).setLikes(likedVideos + 1);
        // adding the video to the map usersLikesVideo
        this.usersLikedVideos.get(user.getId()).add(video);
// removing the user from the map passiveUsers (because he has liked a video)
        this.passiveUsers.remove(user.getId());
    }

    @Override
    public void dislikeVideo(User user, Video video) throws IllegalArgumentException {
        // exception by condition
        if (!contains(user) || !contains(video)) {
            throw new IllegalArgumentException();
        }

        // getting the number of the dislikes of the given video (using the map videosById)
        int dislikedVideos = this.videosById.get(video.getId()).getDislikes();
        // incrementing the dislikes by 1 using a setter
        this.videosById.get(video.getId()).setDislikes(dislikedVideos + 1);
        // adding the video to the map usersDislikesVideos
        this.usersDislikedVideos.get(user.getId()).add(video);
// removing the user from the map passiveUsers (because he has disliked a video)
        this.passiveUsers.remove(user.getId());

    }

    // returns all users from the map passiveUsers
    @Override
    public Iterable<User> getPassiveUsers() {
        return passiveUsers.values();
    }

    @Override
    public Iterable<Video> getVideosOrderedByViewsThenByLikesThenByDislikes() {
        // returns the videos from the map videosById sorted by:
           //  1. views in descending order
           //  2. likes in descending order
           //  3. dislikes in ascending order

        return this.videosById.values()
                .stream()
                .sorted((v1, v2) -> {
                    int result = Integer.compare(v2.getViews(), v1.getViews());

                    if (result == 0) {
                        result = Integer.compare(v2.getLikes(), v1.getLikes());
                    }

                    if (result == 0) {
                        result = Integer.compare(v1.getDislikes(), v2.getDislikes());
                    }

                    return result;
                })
                .collect(Collectors.toList());

    }

    @Override
    public Iterable<User> getUsersByActivityThenByName() {
        // returns all users sorted by:
        //   1. count of the videos they have watched in descending order
        //   2. count of the videos they have liked or disliked? in descending order
        //   3. their username in ascending order

        return this.usersById.values()
                .stream()
                .sorted((user1, user2) -> {
                    int firstUserWatched = this.usersWatchedVideos.get(user1.getId()).size();
                    int secondUserWatched = this.usersWatchedVideos.get(user2.getId()).size();

                    int result = Integer.compare(secondUserWatched, firstUserWatched);

                    if (result == 0) {
                        int firstUserLiked = this.usersLikedVideos.get(user1.getId()).size();
                        int secondUserLiked = this.usersLikedVideos.get(user2.getId()).size();

                        result = Integer.compare(secondUserLiked, firstUserLiked);
                    }

                    if (result == 0) {
                        int firstUserDisliked = this.usersDislikedVideos.get(user1.getId()).size();
                        int secondUserDisliked = this.usersDislikedVideos.get(user2.getId()).size();

                        result = Integer.compare(secondUserDisliked, firstUserDisliked);
                    }


                    if (result == 0) {
                        result = user1.getUsername().compareTo(user2.getUsername());
                    }

                    return result;
                })
                .collect(Collectors.toList());


    }
}
