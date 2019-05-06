package edu.saddleback.microservices.product;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // lol k8s thinks the pod is crashing if I don't give it something to keep busy
        while (true) {
            Thread.sleep(5000);
        }
    }

    public void setProduct(String product){
        //Making some random text so I can test the build

    }

}
