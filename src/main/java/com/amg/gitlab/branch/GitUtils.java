package com.amg.gitlab.branch;

import com.amg.gitlab.cmd.Command;
import com.amg.gitlab.config.Constant;
import com.amg.gitlab.http.HttpUtil;
import com.amg.gitlab.io.FileUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Map;


public class GitUtils {

    public static void cloneBranch(String branch) {
        FileUtil.initDirectory(Constant.GITLAB_WORK_DIR);
        for (String s : Constant.PROJECT_LIST) {
            String cmd = "git clone -b " + branch + " " + Constant.PROJECTS_URL_BASE + s + " " + Constant.GITLAB_WORK_DIR + "\\\\" + s;
            Command.execute(cmd, null);
            if (new File(Constant.GITLAB_WORK_DIR + "\\\\" + s).exists()) {
                System.out.println(s + ":" + branch + " clone success");
            } else {
                throw new RuntimeException(s + " : " + branch + " 远程分支不存在");
            }
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>clone branch execute complete>>>>>>>>>>>>>>>>>>");
    }


    public static void checkoutNewBranch(String branch) {
        for (String s : Constant.PROJECT_LIST) {
            File file = new File(Constant.GITLAB_WORK_DIR + "\\\\" + s);
            if (!isExistOfRemoteBranch(branch, file)) {
                Command.execute("git checkout -b " + branch, file);
            } else {
                System.out.println(s + " : " + branch + " 远程分支已存在，跳过执行 checkoutNewBranch");
            }
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>checkout new branch execute complete>>>>>>>>>>>>>>>>>>");
    }


    public static void pushNewBranch(String branch) {
        for (String s : Constant.PROJECT_LIST) {
            File file = new File(Constant.GITLAB_WORK_DIR + "\\\\" + s);
            if (!isExistOfRemoteBranch(branch, file)) {
                Command.execute("git add .", file);
                Command.execute("git commit -m \"create new branch\"", file);
                Command.execute("git push origin " + branch, file);
            } else {
                System.out.println(s + " : " + branch + " 远程分支已存在，跳过执行 pushNewBranch");
            }
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>push new branch execute complete>>>>>>>>>>>>>>>>>>");
    }


    public static void deleteRemoteBranch(String branch) {
        for (String s : Constant.PROJECT_LIST) {
            File file = new File(Constant.GITLAB_WORK_DIR + "\\\\" + s);
            Command.execute("git push origin --delete " + branch, file);
            System.out.println(s + " : " + branch + " 远程分支删除成功");
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>delete remote branch execute complete>>>>>>>>>>>>>>>>>>");
    }


    public static boolean isExistOfRemoteBranch(String remoteBranch, File filePath) {
        String result = Command.execute("git branch -r", filePath);
        return result.contains("origin/" + remoteBranch);
    }


    public static void createNewBranch(String oldBranch, String newBranch) {
        // 拉取指定分支
        GitUtils.cloneBranch(oldBranch);
        // 创建新分支
        GitUtils.checkoutNewBranch(newBranch);
        // 修改版本号
        FileUtil.modifyVersion(oldBranch, newBranch);
        // 推送新分支到远程
        GitUtils.pushNewBranch(newBranch);
    }


    public static void protectedBranch(String branchName, String projectName) throws Exception {
        if (StringUtils.isBlank(projectName)) {
            for (Object k : Constant.projectMap.keySet()) {
                System.out.println(HttpUtil.post(String.format(Constant.PROTECTED_BRANCHES_URL, Constant.projectMap.get(k), branchName), null));
            }
        } else {
            System.out.println(HttpUtil.post(String.format(Constant.PROTECTED_BRANCHES_URL, Constant.projectMap.get(projectName), branchName), null));
        }
    }


    public static void unprotectBranch(String branchName, String projectName) throws Exception {
        if (StringUtils.isBlank(projectName)) {
            for (Object k : Constant.projectMap.keySet()) {
                System.out.println(HttpUtil.delete(String.format(Constant.UNPROTECT_BRANCHES_URL, Constant.projectMap.get(k), branchName), null));
            }
        } else {
            System.out.println(HttpUtil.delete(String.format(Constant.UNPROTECT_BRANCHES_URL, Constant.projectMap.get(projectName), branchName), null));
        }
    }

}
