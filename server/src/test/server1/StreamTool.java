package test.server1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class StreamTool {
	/**
	 * 读取一行
	 * @param inStream  输入流
	 * @return 字符窜
	 * @throws IOException
	 */
	public static String readLine(InputStream inStream) throws IOException {
		char[] buf = new char[128];
		int room = buf.length;
		int offset = 0;
		int c;
		loop: while (true) {
			switch (c = inStream.read()) {
			case -1:
			case '\n':

				break loop;
			case '\r':
				int c2 = inStream.read();
				if (c2 != '\n' && c2 != -1)
					((PushbackInputStream) inStream).unread(c2);
				break loop;
			default:
				if (--room < 0) {
					char[] lineBuffer = buf;
					buf = new char[offset + 128];
					room = buf.length - offset - 1;
					System.arraycopy(lineBuffer, 0, buf, 0, offset);
				}
				buf[offset++] = (char) c;
				break;
				
			}
		}

		if (c == -1 && offset == 0)
			return null;
		return String.copyValueOf(buf,0,offset);
	}
}
