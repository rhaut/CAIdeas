package com.rkhaut.posted.emission;

import io.socket.emitter.Emitter;

public class Connect {

    private static class ConnectListener implements Emitter.Listener {

        private final Receiver resultReceiver;

        public ConnectListener(Receiver resultReceiver) {
            this.resultReceiver = resultReceiver;
        }

        @Override
        public void call(Object... args) {
            this.resultReceiver.connectResult();
        }
    }

    public static ConnectListener Listener(Receiver resultReceiver) {
        return new ConnectListener(resultReceiver);
    }

    public interface Receiver {
        void connectResult();
    }
}
