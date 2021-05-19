package mofa.wangzhe.reactive.util.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.ToString;

import java.util.Objects;

/**
 * 统一返回工具类
 *
 * @author LD
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ResultUtil {

    /**
     * 成功与否的状态
     */
    private final boolean state;

    /**
     * 提示信息
     */
    private final String msg;

    /**
     * 返回的数据信息
     */
    private final Object data;

    private ResultUtil(Build build) {
        this.state = build.state;
        this.msg = build.msg;
        this.data = build.data;
    }

    public static class Build {
        private final boolean state;
        private final String msg;

        private Object data = null;

        public Build(boolean state, String msg) {
            this.state = state;
            this.msg = msg;
        }

        /**
         * @param data 业务数据，默认null
         * @return build对象
         */
        public Build data(Object data) {
            this.data = data;
            return this;
        }

        public ResultUtil build() {
            return new ResultUtil(this);
        }
    }

    public static ResultUtil ok(Object data) {
        Build b = new Build(true, "成功");
        if (Objects.isNull(data)) {
            return b.build();
        } else {
            return b.data(data).build();
        }
    }

    public final static ResultUtil OK = ok(null);

    public static ResultUtil err(Object data) {
        Build b = new Build(false, "失败");
        if (Objects.isNull(data)) {
            return b.build();
        } else {
            return b.data(data).build();
        }
    }

    public static ResultUtil err2(String msg) {
        Build b = new Build(false, "失败");
        if (Objects.isNull(msg)) {
            return b.build();
        } else {
            return b.data(msg).build();
        }
    }

    public final static ResultUtil ERR = err(null);
}
