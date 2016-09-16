package com.rkhaut.posted.emission;

import io.socket.emitter.Emitter;

public class Disconnect {

    private static class DisconnectListener implements Emitter.Listener {

        private final Receiver resultReceiver;

        public DisconnectListener(Receiver resultReceiver) {
            this.resultReceiver = resultReceiver;
        }

        @Override
        public void call(Object... args) {
            this.resultReceiver.disconnectResult();
        }
    }

    public static DisconnectListener Listener(Receiver resultReceiver) {
        return new DisconnectListener(resultReceiver);
    }

    public interface Receiver {
        void disconnectResult();
    }
}
