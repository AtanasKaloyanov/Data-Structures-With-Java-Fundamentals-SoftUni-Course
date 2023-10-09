package core;

import model.Message;
import shared.DataTransferSystem;

import java.util.*;
import java.util.stream.Collectors;

public class MessagingSystem implements DataTransferSystem {
    private TreeMap<Integer, Message> messagesByWeights;

    private TreeSet<Integer> messagesSet;

     int messagesSize;

    public MessagingSystem() {
        this.messagesByWeights = new TreeMap<>();
        this.messagesSet = new TreeSet<>();

    }    @Override
    public void add(Message message) {
        if (this.messagesByWeights.containsKey(message.getWeight())) {
            throw new IllegalArgumentException();
        }

        this.messagesByWeights.put(message.getWeight(), message);
        this.messagesSet.add(message.getWeight());
        this.messagesSize++;
    }

    @Override
    public Message getByWeight(int weight) {
        if (!this.messagesByWeights.containsKey(weight)) {
            throw new IllegalArgumentException();
        }

        return this.messagesByWeights.get(weight);
    }

    @Override
    public Message getLightest() {
        if (messagesByWeights.isEmpty()) {
            throw new IllegalArgumentException();
        }

        int searchedElementWeight = messagesSet.first();
        return this.messagesByWeights.get(searchedElementWeight);
    }

    @Override
    public Message getHeaviest() {
        if (messagesByWeights.isEmpty()) {
            throw new IllegalArgumentException();
        }

        int searchedElementWeight = messagesSet.last();
        return this.messagesByWeights.get(searchedElementWeight);
    }

    @Override
    public Message deleteLightest() {
        if (messagesByWeights.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.messagesSet.remove(messagesSet.first());
        this.messagesSize--;

        return this.messagesByWeights.remove(messagesSet.first());
    }

    @Override
    public Message deleteHeaviest() {
        if (messagesByWeights.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.messagesSet.remove(messagesSet.last());
        this.messagesSize--;

        return this.messagesByWeights.remove(messagesSet.last());
    }

    @Override
    public Boolean contains(Message message) {
        return this.messagesByWeights.containsKey(message.getWeight());
    }

    @Override
    public List<Message> getOrderedByWeight() {
        return new ArrayList<>(this.messagesByWeights.values());
    }

    @Override
    public List<Message> getPostOrder() {
        if (this.messagesSet.isEmpty()) {
            return null;
        }

        List<Integer> list = new ArrayList<>();
//        postOrderRecursive(this.messagesSet.first(), this.messagesSet, list);


        return list.stream()
                .map( (element) -> this.messagesByWeights.get(element))
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> getPreOrder() {
        if (this.messagesSet.isEmpty()) {
            return null;
        }

        List<Integer> list = new ArrayList<>();
//        preOrderRecursive(this.messagesSet.first(), this.messagesSet, list);

        return list.stream()
                .map( (element) -> this.messagesByWeights.get(element))
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> getInOrder() {
        if (this.messagesSet.isEmpty()) {
            return null;
        }

        List<Integer> list = new ArrayList<>();
//        inOrderRecursive(this.messagesSet.first(), this.messagesSet, list);


        return list.stream()
                .map( (element) -> this.messagesByWeights.get(element))
                .collect(Collectors.toList());

    }

    private static void postOrderRecursive(Integer currentNode, TreeSet<Integer> treeSet, List<Integer> list) {
        if (currentNode != null) {
            postOrderRecursive(treeSet.lower(currentNode), treeSet, list);
            postOrderRecursive(treeSet.higher(currentNode), treeSet, list);
            list.add(currentNode);
        }
    }

    private static void preOrderRecursive(Integer currentNode, TreeSet<Integer> treeSet, List<Integer> list) {
        if (currentNode != null) {
            list.add(currentNode);
            preOrderRecursive(treeSet.lower(currentNode), treeSet, list);
            preOrderRecursive(treeSet.higher(currentNode), treeSet, list);
        }
    }

    private static void inOrderRecursive(Integer currentNode, TreeSet<Integer> treeSet, List<Integer> list) {
        if (currentNode != null) {
            inOrderRecursive(treeSet.lower(currentNode), treeSet, list);
            list.add(currentNode);
            inOrderRecursive(treeSet.higher(currentNode), treeSet, list);
        }
    }


    @Override
    public int size() {
        return this.messagesSize;
    }
}
