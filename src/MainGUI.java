
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import de.dfki.mycbr.core.similarity.AmalgamationFct;
import de.dfki.mycbr.core.similarity.config.AmalgamationConfig;



public class MainGUI {
	public AmalgamationFct WEIGHTED_SUM;
	public JPanel       Inframe, Outframe, Buttonframe, Labelframe, Fieldframe;
	public JTextField   InputBudget, InputType, InputDuration, InputNumberofplaces, InputNumberofcases, InputAmalgam;
	public JEditorPane  Output,Info;
	public JLabel       InputlabelBudget, InputlabelType, InputlabelDuration, InputlabelNumberofplaces, InputlabelNumberofcases, InputlabelAmalgam, OutputlabelAmalgam;
	public JButton      SubmitQuery;
	public JScrollPane  Scroll;
	public Reco  reco;
	
	public JComboBox comboBudget, comboType, comboCases;
	String[] budgetStrings = { "LOW", "MEDIUM", "HIGH" };
	String[] typeStrings = { "SHORT TOUR", "LONG TOUR", "VERY LONG TOUR" };
	String[] casesStrings = {"1", "2","3","4","5","6","7","8","9","10"};
	
	public MainGUI() {                                               

		comboBudget = new JComboBox(budgetStrings);
		comboBudget.setSelectedIndex(0);
		
		comboType = new JComboBox(typeStrings);
		comboType.setSelectedIndex(0);
		
		comboCases = new JComboBox(casesStrings);
		comboCases.setSelectedIndex(4);
		
		InputBudget = new JTextField(10);	
		InputlabelBudget = new JLabel(" How would you rate your Budget ? ");

		InputType = new JTextField(10);	
		InputlabelType = new JLabel(" Could you let us know your Tour Type ? ");

		InputDuration = new JTextField("4", 10);	
		InputlabelDuration = new JLabel(" What would be the Duration of your stay in Singapore ? ");

		InputNumberofplaces = new JTextField("3", 10);	
		InputlabelNumberofplaces = new JLabel(" How many tourist Places would you like to visit during your stay ? ");

		InputNumberofcases = new JTextField("5", 10);	
		InputlabelNumberofcases = new JLabel(" How many suggestions would you like to retrieve ? ");

		InputAmalgam = new JTextField(10);	
		InputlabelAmalgam = new JLabel("Amalgamationfunction to use:");

		Output = new JEditorPane("text/html","<b>Welcome to SGTripAdvisor</b>");
		Output.setEditable(false);                

		Scroll = new JScrollPane(Output);          						                
		Scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		Scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Scroll.setBorder(BorderFactory.createTitledBorder("User dialog:"));
		Scroll.getViewport().setPreferredSize(new Dimension(850,500));

		SubmitQuery = new JButton("Submit Query");
		SubmitQuery.setToolTipText("Press me to process the Query.");
		
		Inframe = new JPanel();                                 
		Inframe.setLayout(new GridLayout(6,2));
				
		Inframe.add(InputlabelBudget);                          
		//Inframe.add(InputBudget);
		Inframe.add(comboBudget);
		
		Inframe.add(InputlabelType); 
		//Inframe.add(InputType);
		Inframe.add(comboType);
		
		Inframe.add(InputlabelDuration);
		Inframe.add(InputDuration);			
		Inframe.add(InputlabelNumberofplaces);
		Inframe.add(InputNumberofplaces);
		Inframe.add(InputlabelNumberofcases);
		//Inframe.add(InputNumberofcases);
		Inframe.add(comboCases);
		
		
		//Inframe.add(InputlabelAmalgam);
		//Inframe.add(InputAmalgam);
		
		Outframe = new JPanel();                           
		Outframe.setSize(new Dimension(700,500));
		Outframe.add(Scroll);

		JFrame Main = new JFrame("Singapore Trip Advisor");   
		
		Main.getContentPane().setLayout(new BorderLayout());
		Main.getContentPane().add(Inframe, BorderLayout.NORTH);
		Main.add(SubmitQuery, BorderLayout.SOUTH);
		Main.getContentPane().add(Outframe, BorderLayout.CENTER);			

		reco = new Reco();
		reco.loadengine();
		
		//Output.setText(reco.displayAmalgamationFunctions());
		Output.setText(reco.displayWelcomeMessage());
		
		InputAmalgam.setText(reco.myConcept.getActiveAmalgamFct().getName());

		Main.addWindowListener(new WindowAdapter() {       

			public void windowClosing(WindowEvent e) {     
				
				System.exit(0);                             
			}
		});

		SubmitQuery.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {

				setAmalgamationFunction();
				
				String recomendation = reco.solveOuery( (String) comboBudget.getSelectedItem(),
						String.valueOf((String) comboType.getSelectedItem()),
						Integer.valueOf(InputDuration.getText()),
						Integer.valueOf(InputNumberofplaces.getText()),
						Integer.valueOf((String) comboCases.getSelectedItem())
						);
				
				/*
				String recomendation = reco.solveOuery(InputBudget.getText(),
						String.valueOf(InputType.getText()),
						Integer.valueOf(InputDuration.getText()),
						Integer.valueOf(InputNumberofplaces.getText()),
						Integer.valueOf(InputNumberofcases.getText())
						);
				*/
				Output.setText(recomendation);
			}
		});

		InputBudget.addActionListener(new ActionListener() {      
			public void actionPerformed(ActionEvent e) {    

				InputBudget.setText("");
			}
		});
		Main.pack();
		Main.setSize(950,700);
		Main.setLocationRelativeTo(null);
		Main.setVisible(true);                               
	}                                                      

	public void setAmalgamationFunction(){
		reco.myConcept.setActiveAmalgamFct(WEIGHTED_SUM);
	}
	
	public void CheckforAmalgamSelection(){

		List<AmalgamationFct> liste = reco.myConcept.getAvailableAmalgamFcts();

		for (int i = 0; i < liste.size(); i++){

			if  ((liste.get(i).getName()).equals(InputAmalgam.getText())) {

				reco.myConcept.setActiveAmalgamFct(liste.get(i));
				
			}		 
		}			
	}

	public static void main(String[] args) {                
		
		MainGUI mygui = new MainGUI();
		
	}
}
