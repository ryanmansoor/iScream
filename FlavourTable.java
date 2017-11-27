package iScream;


import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.UIManager;



import javax.swing.JLabel;
import javax.swing.JTextField;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class FlavourTable extends JFrame {
	DefaultTableModel model;
	public static FlavoursLinkedList FlavourDB = new FlavoursLinkedList();

	public static JTable table;
	private JTextField txtSearch;
	

	public FlavourTable() {
		super();


		model = new DefaultTableModel();
		model.addColumn("Flavour ID"); 
		model.addColumn("Flavour Name");
		model.addColumn("Scoop Price");
		model.addColumn("Flavour Type");
		model.addColumn("Weekly Stock");
		model.addColumn("Current Stock");


		String FlavourInfo[];
		int counter=1;
		String fileName =  System.getProperty("user.dir") + File.separator +"iSceamFiles"+ File.separator +"FlavoursLinkedList.txt";
		try {
			String line;

			File file = new File(fileName);

			Scanner in = new Scanner(file);



			while (in.hasNext()) // continue until end of file
			{
				Flavour newFlavour= new Flavour();
				line = in.next();

				if ((line.charAt(0)) == ('*')){
					if (in.hasNext()){
						line=in.next();
					}else{break;


					}
				}

				FlavourInfo = line.split(",",6);





				newFlavour.FlavourId = Integer.parseInt(FlavourInfo[0]);
				newFlavour.FlavourName = FlavourInfo[1];
				newFlavour.ScoopPrice = Double.parseDouble(FlavourInfo[2]);
				newFlavour.FlavourType = FlavourInfo[3];
				newFlavour.WeeklyStock = Integer.parseInt(FlavourInfo[4]);
				newFlavour.CurrentStock = Integer.parseInt(FlavourInfo[5]);

				String[] Flavour = { FlavourInfo[0], FlavourInfo[1].replaceAll("_"," "), FlavourInfo[2],
						FlavourInfo[3], FlavourInfo[4], FlavourInfo[5] };
				model.addRow(Flavour);
				FlavourDB.add(newFlavour);


				counter ++;
				


			}
			in.close();

		} catch (FileNotFoundException iox) {
			//catches errors
			System.out.println("Problem reading " + fileName);
		}



		table = new JTable(model);
		JPanel inputPanel = new JPanel();
		inputPanel.setBounds(0, 0, 572, 10);

		Container container = getContentPane();
		getContentPane().setLayout(null);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 103, 572, 329);
		container.add(scrollPane);
		container.add(inputPanel);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String x = String.valueOf(model.getValueAt(table.getSelectedRow(), 0));
				int deletedID = Integer.parseInt(x);
				System.out.println(deletedID);

				int counter=1;
				String fileName =  System.getProperty("user.dir") + File.separator +"iSceamFiles"+ File.separator +"FlavoursLinkedList.txt";
				String tempFileName	=	 System.getProperty("user.dir") + File.separator +"iSceamFiles"+ File.separator +"FlavoursTempFile.txt";
				File inputFile = new File(fileName);
				File tempFile = new File(tempFileName);
				try{
					BufferedReader reader = new BufferedReader(new FileReader(inputFile));
					BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

				
					String currentLine;

					while((currentLine = reader.readLine()) != null) {
						
						String trimmedLine = currentLine.trim();
						int IDInTextFile = Character.getNumericValue(trimmedLine.charAt(0));
						System.out.println("Number = " + IDInTextFile);
						System.out.println(deletedID);
						
						if(IDInTextFile == deletedID){
					
						String tempLine = "*"+ trimmedLine;
					
						writer.write(tempLine + System.getProperty("line.separator"));
						}else{
						
							writer.write(currentLine + System.getProperty("line.separator"));
						}
					}
					
					writer.close(); 
					reader.close(); 
					inputFile.delete();
					tempFile.renameTo(inputFile);
				}catch (IOException iox){System.out.println("Problem writing " + fileName );

				} 

		


				model.removeRow(table.getSelectedRow());
				msgBox.infoBox("Flavour has been successfully deleted", "Success");
				
			}
		});
		btnDelete.setBounds(10, 435, 90, 28);
		getContentPane().add(btnDelete);

		JButton btnAddFlavour = new JButton("Add Flavour");
		btnAddFlavour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				FormFlavours s = new FormFlavours();
				s.setVisible(true);
				if (table.getRowCount()!=0){
					FormFlavours.txtFlavourId.setText(String.valueOf((Integer.parseInt((String) FlavourTable.table.getValueAt(FlavourTable.table.getRowCount()-1, 0 ))) + 1));	
					}else{FormFlavours.txtFlavourId.setText("0");
				}

			}
		});
		btnAddFlavour.setBounds(0, 9, 175, 32);
		getContentPane().add(btnAddFlavour);

		JButton btnMainMenu = new JButton("Main Menu");
		btnMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainMenu s = new MainMenu();
				s.setVisible(true);
				
			}
		});
		btnMainMenu.setBounds(204, 9, 175, 31);
		getContentPane().add(btnMainMenu);
		
		JLabel recoverData = new JLabel("To recover deleted records contact the administrator");
		recoverData.setBounds(204, 429, 299, 40);
		getContentPane().add(recoverData);
		
		txtSearch = new JTextField();
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			 public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){ 
					for(int i = 0; i < table.getRowCount(); i++){
				        for(int j = 0; j < table.getColumnCount(); j++){
				            if(((String) table.getModel().getValueAt(i, j)).toLowerCase().equals(txtSearch.getText().toLowerCase())){
				            	msgBox.infoBox("ID: "+table.getModel().getValueAt(i,0)+"\n"+"Name: "+table.getModel().getValueAt(i,1)+" \n"+"Price: "+table.getModel().getValueAt(i,2)+" \n"+"Type: "+table.getModel().getValueAt(i,3)+" \n"+"Weekly Stock: "+table.getModel().getValueAt(i,4)+" \n"+"Current Stock: "+table.getModel().getValueAt(i,5)+" \n","Search Results");
				            }
				        }
				    }
					
	    } 
			}
		});
	
		txtSearch.setBounds(113, 63, 235, 28);
		getContentPane().add(txtSearch);
		txtSearch.setColumns(10);
		
		JLabel lblSearch = new JLabel("Search:");
		lblSearch.setBounds(45, 69, 55, 16);
		getContentPane().add(lblSearch);
		
		
		JButton btnSearch = new JButton("Search");
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < table.getRowCount(); i++){//For each row
			        for(int j = 0; j < table.getColumnCount(); j++){//For each column in that row
			            if(((String) table.getModel().getValueAt(i, j)).toLowerCase().equals(txtSearch.getText().toLowerCase())){//Search the model
			            	msgBox.infoBox("ID: "+table.getModel().getValueAt(i,0)+"\n"+"Name: "+table.getModel().getValueAt(i,1)+" \n"+"Price: "+table.getModel().getValueAt(i,2)+" \n"+"Type: "+table.getModel().getValueAt(i,3)+" \n"+"Weekly Stock: "+table.getModel().getValueAt(i,4)+" \n"+"Current Stock: "+table.getModel().getValueAt(i,5)+" \n","Search Results");
			            }
			        }//For loop inner
			    }//For loop outer
			}
			
		});
		
		btnSearch.setBounds(360, 63, 90, 28);
		getContentPane().add(btnSearch);
		final String FILE = System.getProperty("user.home") + File.separator +"Documents"+File.separator+"FlavourReport.pdf";
		JButton btnCreateReport = new JButton("Create Report");
		
		btnCreateReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

		        try {
		            Document doc = new Document();
		            PdfWriter.getInstance(doc, new FileOutputStream(FILE));
		            doc.open();
		            //Creates a Table in the PDF with the same number of columns as the JTable
		            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
		            //Populates table fields
		            for (int i = 0; i < table.getColumnCount(); i++) {
		                pdfTable.addCell(table.getColumnName(i));
		            }
		           //Creates rows
		            for (int rows = 0; rows < table.getRowCount(); rows++) {
		                for (int cols = 0; cols < table.getColumnCount(); cols++) {
		                	//Populates table records
		                    pdfTable.addCell(table.getModel().getValueAt(rows, cols).toString());

		                }
		            }
		            //adds table to PDF document
		            doc.add(pdfTable);
		            doc.close();
		            msgBox.infoBox("Report created and saved to "+ FILE, "Success");
		        } catch (DocumentException ex) {
		            System.out.println("Document Exception");
		        } catch (FileNotFoundException ex) {
		            System.out.println("File Not Found");
		        }

		    }
		});
		btnCreateReport.setBounds(457, 63, 105, 28);
		getContentPane().add(btnCreateReport);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(596, 539);
		setVisible(true);
	} 
	
	
	
        
	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		new FlavourTable();
		//--------------------------------------------------


		String FlavourInfo[];
		int counter=1;
		String fileName =  System.getProperty("user.dir") + File.separator +"iSceamFiles"+ File.separator +"FlavoursLinkedList.txt";
		try {
			String line;

			File file = new File(fileName);

			Scanner in = new Scanner(file);


			MainLoop:
			while (in.hasNext()) // continue until end of file
			{

				Flavour newFlavour= new Flavour();
				line = in.next();
				if ((line.charAt(0)) == ('*')){
					if (in.hasNext()){
						line=in.next();
					}else{break MainLoop;
					
				}
				}
				FlavourInfo = line.split(",",6);





				newFlavour.FlavourId = Integer.parseInt(FlavourInfo[0]);
				newFlavour.FlavourName = FlavourInfo[1];
				newFlavour.ScoopPrice = Double.parseDouble(FlavourInfo[2]);
				newFlavour.FlavourType = FlavourInfo[3];
				newFlavour.WeeklyStock = Integer.parseInt(FlavourInfo[4]);
				newFlavour.CurrentStock = Integer.parseInt(FlavourInfo[5]);




				FlavourDB.add(newFlavour, counter);






				System.out.println(FlavourDB.get(counter).FlavourId + "\n");
				System.out.println(FlavourDB.get(counter).FlavourName + "\n");
				System.out.println(FlavourDB.get(counter).ScoopPrice + "\n");
				System.out.println(FlavourDB.get(counter).FlavourType + "\n");
				System.out.println(FlavourDB.get(counter).WeeklyStock + "\n");
				System.out.println(FlavourDB.get(counter).CurrentStock);

				counter ++;



			}
			in.close();

		} catch (FileNotFoundException iox) {
			//catches errors
			System.out.println("Problem reading " + fileName);
		}
		System.out.println(String.valueOf(FlavourDB.size()));



		//------------------------------------------------------------------------------------------
	}
}
