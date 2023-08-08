package class14.myclass14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Code03_BestArrange {
    /**
     * 一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲，给你每一个项目开始的时间和结束的时间
     * 你来安排宣讲的日程，要求会议室进行的宣讲的场次最多，返回最多的宣讲场次
     * <p>
     * 将所有会议按照结束时间从小到大排序，然后按照排序好的会议数组遍历会议，每个会议的结束时间<后一个会议的开始时间，场次+1
     */
    public class Meeting {
        public int start;
        public int end;

        public Meeting(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public class MeetingComparator implements Comparator<Meeting> {
        @Override
        public int compare(Meeting o1, Meeting o2) {
            return o1.end - o2.end;
        }
    }

    public int bestArrange(Meeting[] meeting) {
        Arrays.sort(meeting, new MeetingComparator());
        int startTime = 0;
        int num = 0;
        for (int i = 0; i < meeting.length; i++) {
            if (meeting[i].start >= startTime) {
                num++;
                startTime = meeting[i].end;
            }
        }
        return num;
    }

    // 暴力,nums为已经安排的会议数，即向上返回此条路径下安排的会议数，上层取最大，再向上返回
    // 还需要传入开始时间，也就是上一个会议的结束时间
    public int violence(Meeting[] meetings, int nums, int startTimes) {
        // baseCase，若都安排完了，就是baseCase,有点特殊，因为如果剩下的会议开始时间都小于结束时间，那么在递归中直接返回上一层，不会使用baseCase
        if (meetings.length == 0) {
            return nums;
        }
        // 如果没有进行到下一个递归，或者不能触发baseCase，则向上返回当前nums
        int max = nums;
        for (int i = 0; i < meetings.length; i++) {
            if (meetings[i].start >= startTimes) {
                max = Math.max(max, violence(remove(meetings, i), nums + 1, meetings[i].end));
            }
        }
        return max;
    }

    public Meeting[] remove(Meeting[] meetings, int i) {
        Meeting[] meetings1 = new Meeting[meetings.length - 1];
        int k = 0;
        for (int j = 0; j < meetings.length; j++) {
            if (j != i) {
                meetings1[k++] = meetings[j];
            }
        }
        return meetings1;
    }
}
