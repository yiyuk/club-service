package net.lab1024.smartadmin.module.system.position;

import net.lab1024.smartadmin.common.constant.ResponseCodeConst;

/**
 * @author zzr
 */
public class PositionResponseCodeConst extends ResponseCodeConst {

    public static final PositionResponseCodeConst REMOVE_DEFINE = new PositionResponseCodeConst(13000, "该社团内还有成员,不能删除");

    public static final PositionResponseCodeConst UPDATE_RELATION_DEFINE = new PositionResponseCodeConst(13100, "关联状态错误");

    protected PositionResponseCodeConst(int code, String msg) {
        super(code, msg);
    }

}
