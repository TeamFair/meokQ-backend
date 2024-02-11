package data

import com.meokq.api.notice.enums.NoticeTarget
import com.meokq.api.notice.model.Notice
import jdk.jfr.Description

object MockNotice {
    /**
     * for customer
     */
    @Description("Notice1 for Customer")
    val NT20000001 = Notice(
        noticeId = "NT20000001",
        title = "Customer Notice title1",
        content = "Content for customers",
        target = NoticeTarget.CUSTOMER
    )

    @Description("Notice1 for Customer")
    val NT20000002 = Notice(
        noticeId = "NT20000002",
        title = "Customer Notice title2",
        content = "Content for customers",
        target = NoticeTarget.CUSTOMER
    )

    /**
     * for boss
     */
    @Description("Notice1 for Boss")
    val NT20000003 = Notice(
        noticeId = "NT20000003",
        title = "Boss Notice title1",
        content = "Content for bosses",
        target = NoticeTarget.BOSS
    )

    @Description("Notice2 for Boss")
    val NT20000004 = Notice(
        noticeId = "NT20000004",
        title = "Boss Notice title2",
        content = "Content for bosses",
        target = NoticeTarget.BOSS
    )

    @Description("Notice3 for Boss")
    val NT20000005 = Notice(
        noticeId = "NT20000005",
        title = "Boss Notice title3",
        content = "Content for bosses",
        target = NoticeTarget.BOSS
    )

    /**
     * for all
     */

    @Description("Notice1 for All")
    val NT20000006 = Notice(
        noticeId = "NT20000006",
        title = "Notice title",
        content = "Content for all",
        target = NoticeTarget.ALL
    )
}