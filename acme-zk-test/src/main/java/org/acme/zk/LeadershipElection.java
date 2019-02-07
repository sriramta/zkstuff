package org.acme.zk;

import java.io.Closeable;
import java.io.IOException;
import javax.annotation.PreDestroy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LeadershipElection extends LeaderSelectorListenerAdapter implements Closeable {

    @Value("${spring.application.name:zk-server}")
    private String electionRoot;

    private volatile boolean isLeader = false;
    private static final long TENURE_MS = 2000L;

    private final LeaderSelector selector;

    @Autowired
    public LeadershipElection(CuratorFramework client) {
        selector = new LeaderSelector(client, "/" + electionRoot, this);
        selector.autoRequeue();
        selector.start();
    }

    @EventListener
    public void onStart(ApplicationReadyEvent event) {

    }


    @Override
    public void close() throws IOException {
        revokeLeadership();
    }

    @Override
    public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
        isLeader = true;
        System.out.println("Leader elected as this");
        while(isLeader) {
            try {
                Thread.sleep(TENURE_MS);
            } catch (InterruptedException ex) {
                // nothing to do
            }
        }

        revokeLeadership();
    }

    private void revokeLeadership() {
        isLeader = false;
        System.out.println("Lost Leadership...");
    }

    @PreDestroy
    public void preDestroy() {
        isLeader = false;
    }

    @Around("@annotation(org.acme.zk.annotation.LeaderOnly)")
    public void onlyExecuteForLeader(ProceedingJoinPoint joinPoint) {
        if (!isLeader) {
            System.out.println("I'm not leader, skip leader-only tasks.");
            return;
        }

        try {
            System.out.println("I'm leader, execute leader-only tasks.");
            joinPoint.proceed();
        } catch (Throwable ex) {
            System.out.println(ex.getMessage());
        }
    }
}