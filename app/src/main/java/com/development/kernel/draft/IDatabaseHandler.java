package com.development.kernel.draft;

import java.util.List;

/**
 * Created by Acer on 11.12.2016.
 */

interface IDatabaseHandler {
    void addSession(ASession asession);
    List<ASession> getAllSessions();
    void deleteSession(ASession asession);
    void deleteAll();
}