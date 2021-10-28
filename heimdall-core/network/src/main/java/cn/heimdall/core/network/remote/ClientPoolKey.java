package cn.heimdall.core.network.remote;

public class NettyPoolKey {

    private NettyRole nettyRole;
    private String address;

    public NettyPoolKey(NettyRole nettyRole, String address) {
        this.nettyRole = nettyRole;
        this.address = address;
    }


    public NettyRole getNettyRole() {
        return nettyRole;
    }


    public NettyPoolKey setNettyRole(NettyRole nettyRole) {
        this.nettyRole = nettyRole;
        return this;
    }


    public String getAddress() {
        return address;
    }


    public NettyPoolKey setAddress(String address) {
        this.address = address;
        return this;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("transactionRole:");
        sb.append(nettyRole.name());
        sb.append(",");
        sb.append("address:");
        sb.append(address);
        sb.append(",");
        sb.append("msg:< ");
        sb.append(" >");
        return sb.toString();
    }

    public enum ClientRole {
        COMPUTE(1),
        STORAGE(2),
        GUARDER(3),
        APP(4);
        private int value;

        ClientRole(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }


    }
}
