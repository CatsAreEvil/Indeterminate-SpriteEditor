package editor.ui.skeleton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import editor.objects.skeleton.ISkeleton;
import editor.objects.skeleton.Skeleton;
import editor.objects.skeleton.SkeletonAnimation;
import editor.objects.skeleton.SkeletonFrame;
import editor.objects.skeleton.SkeletonPiece;

public class SkeletonTreeControls extends SkeletonUIElement{
	private JButton addAnim, delete, addFrame, rename;
	private JTree tree;

	public SkeletonTreeControls(SkeletonEdit edit, Rectangle bounds, JTree skeletonTree) {
		super(edit, bounds);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.tree = skeletonTree;
		
//		panel.setBackground(Color.DARK_GRAY);	
	
		// Animation controls
		addAnim = new JButton("New Animation");
		delete = new JButton("Delete Animation");
		rename = new JButton("Rename Animation");
		addFrame = new JButton("New Frame");
		
		// Frame controls 
		
		setupControls(SkeletonPiece.ANIMATION);
	}
	
	public void setupControls(SkeletonPiece type)
	{
		ISkeleton selectedNode = (ISkeleton)tree.getLastSelectedPathComponent();
		panel.removeAll();
		switch (type)
		{
		case ANIMATION:
			panel.add(addAnim);
			JSeparator line = new JSeparator();
			line.setPreferredSize(new Dimension(panel.getWidth(), 5));
			panel.add(line);
			if (selectedNode != null)
			{
				JLabel nameLabel = new JLabel(selectedNode.toString(), JLabel.CENTER);
				nameLabel.setPreferredSize(new Dimension(panel.getWidth(), (int)nameLabel.getPreferredSize().getHeight()));
				panel.add(nameLabel);
				line = new JSeparator();
				line.setPreferredSize(new Dimension((int)(panel.getWidth() * 0.6), 1));
				panel.add(line);
			}
			panel.add(rename);
			rename.setText("Rename Animation");
			panel.add(delete);
			delete.setText("Delete Animation");
			line = new JSeparator();
			line.setPreferredSize(new Dimension(panel.getWidth(), 5));
			panel.add(line);
			panel.add(addFrame);
			break;
		case FRAME:
			if (selectedNode != null)
			{
				JLabel nameLabel = new JLabel(selectedNode.toString(), JLabel.CENTER);
				nameLabel.setPreferredSize(new Dimension(panel.getWidth(), (int)nameLabel.getPreferredSize().getHeight()));
				panel.add(nameLabel);
				line = new JSeparator();
				line.setPreferredSize(new Dimension((int)(panel.getWidth() * 0.6), 1));
				panel.add(line);
			}
			panel.add(rename);
			rename.setText("Rename Frame");
			panel.add(delete);
			delete.setText("Delete Frame");
			break;
		default:
			break;
		}
		panel.revalidate();
		panel.repaint();
	}
	
	public void createEvents()
	{
		addAnim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String animName = (String)JOptionPane.showInputDialog(panel, "","Animation Name",
	    				JOptionPane.PLAIN_MESSAGE, null, null, "Animation");
				DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
				Skeleton root = (Skeleton) model.getRoot();
				SkeletonAnimation newAnim = new SkeletonAnimation(animName, root);
				model.insertNodeInto((MutableTreeNode)newAnim, root, root.getChildCount());
				model.reload();
				TreePath path = new TreePath(root).pathByAddingChild(newAnim);
				tree.setSelectionPath(path);
			}
		});
		addFrame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ISkeleton selectedNode = (ISkeleton)tree.getLastSelectedPathComponent();
				if (selectedNode != null)
				{
					TreePath path = tree.getSelectionPath();
					String frameName = (String)JOptionPane.showInputDialog(panel, "","Frame Name",
		    				JOptionPane.PLAIN_MESSAGE, null, null, "Frame");
					DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
					model.insertNodeInto((MutableTreeNode)new SkeletonFrame(frameName, (SkeletonAnimation)selectedNode),
							selectedNode, selectedNode.getChildCount());
					model.reload();
									
					tree.expandPath(path);
					tree.setSelectionPath(path.pathByAddingChild(selectedNode.getChildAt(selectedNode.getChildCount() - 1)));
				}			
			}
		});
		rename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ISkeleton selectedNode = (ISkeleton)tree.getLastSelectedPathComponent();
				if (selectedNode != null)
				{
					TreePath path = tree.getSelectionPath();
					String frameName = (String)JOptionPane.showInputDialog(panel, "","Frame Name",
		    				JOptionPane.PLAIN_MESSAGE, null, null, selectedNode.toString());
					selectedNode.setName(frameName);
					DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
					model.reload();
					
					tree.expandPath(path);
					tree.setSelectionPath(path);
				}
			}
		});
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ISkeleton selectedNode = (ISkeleton)tree.getLastSelectedPathComponent();
				if (selectedNode != null)
				{
					DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
					model.removeNodeFromParent(selectedNode);
				}
			}
		});
	}
}
