package com.stosz.fsm;

import java.time.LocalDateTime;

/**
 * @param <T>
 */
public interface IFsmDao<T extends IFsmInstance> {
    /**
     * @param id
     * @param state
     * @param stateTime
     */
    Integer updateState(Integer id, String state, LocalDateTime stateTime);
}
