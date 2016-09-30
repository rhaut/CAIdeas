package com.rkhaut.posted.listener;

import com.rkhaut.posted.receiver.DisconnectionReceiver;

import io.socket.emitter.Emitter;

public class DisconnectionListener implements Emitter.Listener {

        private final DisconnectionReceiver resultReceiver;

        public DisconnectionListener(DisconnectionReceiver resultReceiver) {
            this.resultReceiver = resultReceiver;
        }

        @Override
        public void call(Object... args) {
            this.resultReceiver.disconnectResult();
        }
    }