package edu.saddleback.microservices.cart;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // lol k8s thinks the pod is crashing if I don't give it something to keep busy
        while (true) {
            Thread.sleep(5000);
        }
    }
}
