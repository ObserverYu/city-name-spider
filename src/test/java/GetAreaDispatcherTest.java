import org.junit.Test;
import org.chen.spider.GetAreaMain;
import org.chen.spider.dispater.GetAreaDispatcher;

import java.lang.reflect.Method;

/**
 * 
 *  
 * @author YuChen
 * @date 2019/12/26 17:33
 **/
 
public class GetAreaDispatcherTest {

    @Test
    public void testGetFinalEntity() throws NoSuchMethodException {
        GetAreaDispatcher dispatcher = GetAreaMain.build();
        Class<GetAreaDispatcher> areaDispatcherClass = GetAreaDispatcher.class;
        Method getFinalEntity = areaDispatcherClass.getDeclaredMethod("getFinalEntity", String.class, String.class);
        //getFinalEntity.invoke(dispatcher,)
    }
}
