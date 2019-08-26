package com.rubanj.casino.rockpaperscissors.service;

import com.rubanj.casino.rockpaperscissors.domain.model.RPSChoice;
import com.rubanj.casino.rockpaperscissors.util.MarkovChainRPS;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RPSChoseMarkovChainProvider {

    private Map<Long, MarkovChainRPS> chainState = new ConcurrentHashMap<>();

    public void initNewChain(Long userGameId) {
        chainState.putIfAbsent(userGameId, MarkovChainRPS.create());
    }

    public void evictChain(Long userGameId) {
        chainState.remove(userGameId);
    }

    public RPSChoice aiChoice(Long userGameId, RPSChoice previousUserChoice) {
        MarkovChainRPS chain = chainState.get(userGameId);
        if (null == chain) {
            initNewChain(userGameId);
            chain = chainState.get(userGameId);
        }
        return chain.aiChoice(previousUserChoice);
    }

    public void updateChain(Long userGameId, RPSChoice previousChoice, RPSChoice currentChoice) {
        MarkovChainRPS chain = chainState.get(userGameId);
        if (null == chain) {
            initNewChain(userGameId);
            chain = chainState.get(userGameId);
        }
        chain.update(previousChoice, currentChoice);
    }

}
