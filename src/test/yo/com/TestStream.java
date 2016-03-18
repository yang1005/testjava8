package test.yo.com;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestStream {

	public static void main(String[] args) throws IOException {

		int sum = IntStream.range(1, 101).sum();
		System.out.println(sum);

		Path path = FileSystems.getDefault().getPath("src/wordcount.txt");

		long start = System.currentTimeMillis();
		Files.lines(path).flatMap(line -> Arrays.stream(line.split("( |\\.|,)+")))// Map
				.collect(Collectors.groupingBy(word -> word, Collectors.counting()))// Reduce
				.forEach((k, v) -> System.out.println(k + ":" + v));// Output
		System.out.println("並行処理なし処理時間：" + (System.currentTimeMillis() - start));

		start = System.currentTimeMillis();
		Files.lines(path).flatMap(line -> Arrays.stream(line.split("( |\\.|,)+")))// Map
				.parallel()// Concurrent
				.collect(Collectors.groupingBy(word -> word, Collectors.counting()))// Reduce
				.forEach((k, v) -> System.out.println(k + ":" + v));// Output
		System.out.println("並行処理あり処理時間：" + (System.currentTimeMillis() - start));

		List<String> list = Arrays.asList("C", "C++", "Java", "Scala", "Ruby");
		long count = list.stream().filter(s -> s.startsWith("C")).mapToInt(s -> s.length()).sum();
		System.out.println("count:" + count);

		path = Paths.get("src/wordcount.txt");
		try (Stream<String> lines = Files.lines(path)) {
			lines.forEach(System.out::println);
		} catch (IOException e) {
		}

		path = Paths.get("src/stream.txt");
		;
		try (Stream<String> lines = Files.lines(path)) {
			lines.filter(line -> !line.startsWith("#")).mapToInt(Integer::parseInt)
					.mapToObj(i -> (i % 3 == 0 ? "xxx" : String.valueOf(i))).forEach(System.out::println);
		} catch (IOException e) {
		}

	}
}
