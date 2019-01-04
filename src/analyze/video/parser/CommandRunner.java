package analyze.video.parser;

import java.io.IOException;

import com.mr.replay.ui.log.Log;

public class CommandRunner {
	public static String execCommands(String[] commands)
	{
		String output = "";
		try {
			Process p = Runtime.getRuntime().exec(commands);
			StreamCaptureThread errorStream = new StreamCaptureThread(p.getErrorStream());
			StreamCaptureThread outputStream = new StreamCaptureThread(p.getInputStream());
			new Thread(errorStream).start();
			new Thread(outputStream).start();
			p.waitFor();
			String outputString = outputStream.output.toString();
			Log.info(outputString);
			String errorString  = errorStream.output.toString();
			Log.err(errorString);
			output  = outputString +"\n"+errorString;
			System.out.println(output);
			
		}  catch (InterruptedException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}
	
	public static String execCommand(String command) {
		String[] commands = command.split("\\s+");
		return execCommands(commands);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//D:\ffmpeg\bin\ffmpeg -i D:\tmp__\test.avi D:\tmp__\12\image%d.jpg
//		CommandRunner cr = new CommandRunner();
//		String[] commands = new String[] {"adb", "pull", remotedir, localdir};
//		System.out.println(cr.execCommands(commands));
		CommandRunner.execCommand("D:\\ffmpeg\\bin\\ffmpeg -i " +
				"D:\\tmp__\\test.avi D:\\tmp__\\12\\%d.jpg");
		
	}
}
