package ru.droidwelt.waiter24.ebus;

public class Events {

    public static final String NETWORK_STATE = "NETWORK_STATE";


    public static class EventsMessage {

        private String mes_code;

        public String getMes_code() {
            return mes_code;
        }

        public void setMes_code(String mes_code) {
            this.mes_code = mes_code;
        }


    }

}

