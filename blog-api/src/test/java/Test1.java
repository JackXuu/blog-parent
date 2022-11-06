import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author jack
 */
public class Test1 {



    @Test
    public void test(){
        TagVo tagVo1 = new TagVo("1", "11", "111");
        TagVo tagVo2 = new TagVo("2", "11", "111");
        List<TagVo> list = new ArrayList<>();
        list.add(tagVo1);
        list.add(tagVo2);
        System.out.println(list);

    }
}
