package bttc.app.model;

import java.util.List;

public class StallInformation {
    private String cell;
    private String email;
    private String name;
    private String payment;
    private String paymentMethod;
    private boolean paymentReceived;
    private String preferedCommunication;
    private String reference;
    private boolean showPaymentMethod;

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isPaymentReceived() {
        return paymentReceived;
    }

    public void setPaymentReceived(boolean paymentReceived) {
        this.paymentReceived = paymentReceived;
    }

    public String getPreferedCommunication() {
        return preferedCommunication;
    }

    public void setPreferedCommunication(String preferedCommunication) {
        this.preferedCommunication = preferedCommunication;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public boolean isShowPaymentMethod() {
        return showPaymentMethod;
    }

    public void setShowPaymentMethod(boolean showPaymentMethod) {
        this.showPaymentMethod = showPaymentMethod;
    }

    public List<Object> getSelectedCategories() {
        return selectedCategories;
    }

    public void setSelectedCategories(List<Object> selectedCategories) {
        this.selectedCategories = selectedCategories;
    }

    private List<Object> selectedCategories;


}
