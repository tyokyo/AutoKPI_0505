package com.mr.replay.ui.merge;

import java.util.Iterator;
import java.util.List;

public class StepGenerator {
	
	Context c = null;
	
	public StepGenerator(String videopath, String scriptpath, int repeat) {
		this.c = new Context(videopath, scriptpath, repeat);
	}

	public void handle() {
		Expression exp = null;
		for(String line: this.c.input) {
			if(Context.isBegin(line)) {
				exp = new Begin();
			}else if(Context.isEnd(line)) {
				exp = new End();
			}else if(Context.isCode(line)) {
				exp = new Code();
			}else if(Context.isComments(line)) {
				exp = new Comments();
			}
			exp.interpret(c);
		}
	}
	
	public String output() {
		return this.c.output.toString();
	}
	
	public static void usage() {
		StepGenerator sg = new StepGenerator("cases", "cases//zq001.py", 3);
		sg.handle();
		System.out.println(sg.output());
	}
	
	public static void main(String[] args) {
		usage();
	}

}

class Context {
	
	public int repeat;
	public String videopath;
	public String scriptpath;
	
	public Context(String videopath, String scriptpath, int repeat) {
		this.videopath = videopath;
		this.scriptpath = scriptpath;
		this.repeat = repeat;
		init();
	}
	
	public void init() {
		this.input = TextFile.read(this.scriptpath);
		this.i = this.input.iterator();
		this.output = new StringBuffer();
		this.isInterpretted = false;
	}
	
	public List<String> input;
	public Iterator<String> i;
	public StringBuffer output;
	boolean isInterpretted;
	
	public String next() {
		String line = null;
		if(this.i.hasNext()) {
			line = i.next();
		}
		if(Context.isBegin(line)) {
			this.isInterpretted = true;
		}
		if(Context.isEnd(line)) {
			this.isInterpretted = false;
		}
		return line;
	}
	
	public static boolean isBegin(String line) {
		if(line.trim().equals("#Start")) {
			return true;
		}
		return false;
	}
	
	public static boolean isEnd(String line) {
		if(line.trim().equals("#End")) {
			return true;
		}
		return false;
	}
	
	public static boolean isCode(String line) {
		if(!line.trim().startsWith("#")) {
			return true;
		}
		return false;
	}
	
	public static boolean isComments(String line) {
		if(line.trim().startsWith("#")) {
			return true;
		}
		return false;
	}
}

abstract class Expression {
	public void interpret(Context context) {
		parse(context);
	}
	
	public abstract void parse(Context context);
}

class Begin extends Expression {
	public void parse(Context context) {
		String line = context.next();
		context.output.append(String.format(
				"# === [%s], %d repeat ===\n", context.scriptpath, context.repeat));
		context.output.append(line + "\n");
		context.output.append(String.format(
				"for i in range(1,%d):\n", context.repeat));
		context.output.append("  sc = vc.getSocketClient(\"127.0.0.1\",9999)\n");
		context.output.append("  postfix = '%.3d' % i\n");
		context.output.append("  name = postfix+\".avi\""+"\n");
		context.output.append(String.format(
				"  videopath = \"%s\"+name"+"\n", context.videopath));
	}
}

class Code extends Expression {
	public void parse(Context context) {
		String line = context.next();
		if(context.isInterpretted) {
			context.output.append("  " + line + "\n");
		}
	}
}

class Comments extends Expression {
	public void parse(Context context) {
		String line = context.next(); //不输出注释
	}
}

class End extends Expression {
	public void parse(Context context) {
		String line = context.next();
		context.output.append(line).append("\n").append("\n");
	}
}