package edu.saddleback.microservices.frontend.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import edu.saddleback.microservices.frontend.controller.AppController;
import edu.saddleback.microservices.frontend.controller.backendcontrollers.MakeOrderController;
import edu.saddleback.microservices.frontend.model.backendmodels.OrderTransaction;

public class CheckoutView {

    private AppController controller;

    @FXML
    private Label orderNumberLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label cryptoTypeLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private ImageView qrImageView;

    public void initialize() {

        controller = App.getController();
        orderNumberLabel.setText("null");
        statusLabel.setText("null");
        cryptoTypeLabel.setText("null");
        priceLabel.setText("null");

        MakeOrderController makeOrderCon = new MakeOrderController(controller.getToken(), controller.getCart(),
                controller.getSelectedCoin());
        makeOrderCon.getOrdersMadeObservableBoolean().subscribe((onOrderMade) -> {

            OrderTransaction orderMade = makeOrderCon.getOrder();
            orderNumberLabel.setText(orderMade.id);
            statusLabel.setText(orderMade.status);
            cryptoTypeLabel.setText(returnCryptoWord(orderMade.coin));
            priceLabel.setText(orderMade.price + " " + orderMade.coin.replaceFirst("t", ""));
            qrImageView.setImage(getQrCodeImage(orderMade.cryptoAddress, 200, 200));

        });

        makeOrderCon.start();

    }

    public void onShopButtonClicked() {

        try {
            App.getCoordinator().showAppScene();
        } catch (Exception e) {
            System.err.println("oof");
        }

    }

    public void onCartImageClicked() {

        try {
            App.getCoordinator().showCartScene();
        } catch (Exception e) {
            System.err.println("oof");
        }

    }

    private Image getQrCodeImage(String text, int width, int height) {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        try {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayInputStream in = new ByteArrayInputStream(pngOutputStream.toByteArray());

        return new Image(in);

    }

    private String returnCryptoWord(String abbreviation) {

        switch (abbreviation) {

            case "tbtc":
                return "Bitcoin (BTC)";
            case "tltc":
                return "Litecoin (LTC)";
            case "tzec":
                return "Zcash (ZEC)";
            default:
                return "Lumens (XLM)";

        }

    }

}
