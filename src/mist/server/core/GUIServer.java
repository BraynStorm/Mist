package mist.server.core;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUIServer extends JFrame {
	
	private static final long serialVersionUID = 7829404624325938315L;
	private JPanel contentPane;
	private JList<String> playerList;
	
	public GUIServer() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent paramWindowEvent) {
				Server.getInstance().stop();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 676, 512);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 28, 200, 434);
		contentPane.add(scrollPane);
		
		playerList = new JList<String>();
		// SetList
		scrollPane.setViewportView(playerList);
		
		JLabel lblPlayer = new JLabel("Players");
		lblPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayer.setLabelFor(scrollPane);
		lblPlayer.setBounds(10, 11, 193, 14);
		contentPane.add(lblPlayer);
	}
	public JList<String> getPlayerList() {
		return playerList;
	}
}
