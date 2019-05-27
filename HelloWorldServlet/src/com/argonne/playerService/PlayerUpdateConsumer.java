package com.argonne.playerService;

import java.util.Hashtable;

public interface PlayerUpdateConsumer {

    public void acceptPlayer(Hashtable courseCollection);
}

