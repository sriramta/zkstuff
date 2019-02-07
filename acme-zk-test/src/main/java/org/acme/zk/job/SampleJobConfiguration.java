package org.acme.zk.job;

import org.acme.zk.annotation.LeaderOnly;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SampleJobConfiguration {

    @Autowired
    private CuratorFramework curatorFramework;

    @Scheduled(fixedRate = 5000)
    @LeaderOnly
    public void reportCurrentTime() {
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> This is the leader and hence printed <<<<<<<<<<<<<<<< ");
    }
}
