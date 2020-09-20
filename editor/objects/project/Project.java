package editor.objects.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Project {
	private List<ProjectEntity> entities;
	private String name;
	private File directory;
	
	public Project()
	{
		entities = new ArrayList<ProjectEntity>();
	}
	public Project(final File directory) throws FileNotFoundException
	{
		this();
		validateDirectory(directory);
		this.directory = directory;
		
		boolean loadProject = true;
		File projectFile = new File(directory + "/PROJECTINFO.txt");
		if (projectFile.exists())
		{
			Scanner projFile = new Scanner(projectFile);
			if (projFile.hasNext())
			{
				String[] tokens = projFile.next().split(":");
				name = tokens[1];
			}
			projFile.close();
		}
		else
		{
			int result = JOptionPane.showConfirmDialog(null, "Folder does not have a PROJECTINFO.txt file. Would you like to create one?");
			if (result == JOptionPane.OK_OPTION)
			{
				name = JOptionPane.showInputDialog("Enter the project name:", "Project");
				File newProjFile = new File(directory + "/PROJECTINFO.txt");
				try {
					newProjFile.createNewFile();
					PrintWriter writer = new PrintWriter(newProjFile);
					writer.print("NAME:" + name + "\n"); 
					writer.close();
				} catch (IOException e) {
					throw new FileNotFoundException("Project file creation failed \n" + e.getMessage());
				}
			}
			else
			{
				loadProject = false;
			}
		}
		
		if (loadProject)
		{
			createAllInDirectory(directory);
		}
	}
	
	private void createAllInDirectory(File directory)
	{
		for (int i = 0; i < directory.listFiles().length; i++)
		{
			if (directory.listFiles()[i].isDirectory())
			{
				createAllInDirectory(directory.listFiles()[i]);
			}
			else
			{
				try {
					ProjectEntity newEntity = ProjectEntityFactory.createFromFile(directory.listFiles()[i]);
					if (newEntity != null)
					{
						entities.add(newEntity);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void validateDirectory(File directory) throws FileNotFoundException
	{
		if(!directory.isDirectory())
		{
			throw new FileNotFoundException(directory.toString() + " is not a directory!");
		}
	}
	
	public List<ProjectEntity> getEntities()
	{
		return entities;
	}
	public String getName()
	{
		return name;
	}
	public File getDirectory()
	{
		return directory;
	}
}
