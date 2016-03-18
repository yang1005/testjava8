package test.yo.com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestList {

	public static void main(String[] args) throws IOException {
		System.out.println("aaaaa");

		List<String> cities = new ArrayList<String>();
		cities.add("京都");
		cities.add("大阪");
		cities.add("神戸");
		// cities.forEach((city) -> System.out.println(city));
		// 既存のメソッドを(::)で呼び出すことでラムダ式化が出来る。
		cities.forEach(System.out::println);

		// ランダムな数字のリストを作成
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		list1.add(5);
		list1.add(-77);
		list1.add(24);
		list1.add(3);
		list1.add(987);
		list1.add(56);
		list1.add(-9);
		list1.add(0);

		// 整列前のリストを出力
		System.out.println("ソート前:");
		list1.forEach(System.out::println);
		// for (int i : list1) {
		// System.out.println(i);
		// }

		// 降順に整列
		Collections.sort(list1, (arg1, arg2) -> arg2 - arg1);

		// 整列後のリストを出力
		System.out.println();
		System.out.println("ソート後:");
		// for (int i : list1) {
		// System.out.println(i);
		// }
		list1.forEach(System.out::println);

	}
}
