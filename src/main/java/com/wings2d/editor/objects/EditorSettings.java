package com.wings2d.editor.objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class EditorSettings {
	private File editorDir;
	private File projectDir;
	
	private static final String FILE_NAME = "PROJECTSETTINGS.txt";
	private static final String CUR_PROJECT_TOKEN = "DIR";
	private static final String SPLIT = ">"; // Don't use ":" due to it being in the path
	
	public EditorSettings()
	{
		editorDir = new File(System.getProperty("user.dir") + "/src/main/resources");
		File editorFile = new File(editorDir + "/" + FILE_NAME);
		if (!editorFile.exists())
		{
			try {
				editorFile.createNewFile();
			} catch (IOException e) {e.printStackTrace();}
		}
		createFromFile(editorFile);
	}

	private void createFromFile(final File file)
	{
		try {
			Scanner in = new Scanner(file);
			while(in.hasNext())
			{
				String[] tokens = in.next().split(SPLIT);
				switch(tokens[0])
				{
				case CUR_PROJECT_TOKEN:
					projectDir = new File(tokens[1]);
					break;
				default:
					break;
				}
				
			}
			in.close();
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
	}
	
	public void saveToFile()
	{
		try {
			PrintWriter out = new PrintWriter(editorDir + "/" + FILE_NAME);
			out.print(""); // Clear the file
			if (projectDir != null)
			{
				out.print(CUR_PROJECT_TOKEN + SPLIT + projectDir.toString());
			}
			out.close();
		} catch (FileNotFoundException e) {e.printStackTrace();}
	}
	
	public File getProjectDirectory()
	{
		return projectDir;
	}
	public void setProjectDirectory(final File dir)
	{
		projectDir = dir;
	}	
}
