package com.amg.gitlab;

import com.amg.gitlab.branch.GitUtils;

public class Run {

    public static void main(String[] args) throws Exception {

        // 基于旧分支创建新分支并推送到远程
        GitUtils.createNewBranch("feature_v1.6.2", "feature_v1.6.3");
        // 删除远程分支
//        GitUtils.deleteRemoteBranch("feature_v1.6.1");

        // 保护分支
//        GitUtils.protectedBranch("feature_v1.1", "fulfillment-order-service");

        // 解除保护分支
       // GitUtils.unprotectBranch("feature_v1.1", "fulfillment-order-service");
    }
}
