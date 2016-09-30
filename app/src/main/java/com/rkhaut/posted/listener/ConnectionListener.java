package com.rkhaut.posted.listener;

import com.rkhaut.posted.receiver.ConnectionReceiver;

import io.socket.emitter.Emitter;

public class ConnectionListener implements Emitter.Listener {

        private final ConnectionReceiver resultReceiver;

        public ConnectionListener(ConnectionReceiver resultReceiver) {
            this.resultReceiver = resultReceiver;
        }

        @Override
        public void call(Object... args) {
            this.resultReceiver.connectResult();
        }
    }