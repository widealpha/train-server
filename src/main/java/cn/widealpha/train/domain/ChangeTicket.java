package cn.widealpha.train.domain;

public class ChangeTicket {
    //id如果需要付钱的话
    private int orderId;
    private String qrLink;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getQrLink() {
        return qrLink;
    }

    public void setQrLink(String qrLink) {
        this.qrLink = qrLink;
    }
}
