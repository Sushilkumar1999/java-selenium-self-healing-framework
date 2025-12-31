package autoheal.Logic.in;

import java.util.ArrayList;
import java.util.List;

public class HealingContext {

	public static ThreadLocal<List<String>> healingMsg = ThreadLocal.withInitial(ArrayList::new);

	public static void setMsg(String msg) {
		healingMsg.get().add(msg);
	}

	public static List<String> getMsg() {
		return healingMsg.get();
	}

	public static void clearMsg() {
		healingMsg.remove();
	}
}
