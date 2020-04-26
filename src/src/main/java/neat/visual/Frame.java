package neat.visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import neat.genomes.Genome;

public class Frame extends JFrame {

  private Panel panel;
  private Genome genome;

  /**
   * Instantiates a new Frame.
   *
   * @param genome the genome
   */
  public Frame(Genome genome) {
    this();
    setGenome(genome);
    this.repaint();
  }

  /**
   * Instantiates a new Frame.
   *
   * @throws HeadlessException the headless exception
   */
  public Frame() throws HeadlessException {
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    this.setTitle("NEAT");
    this.setMinimumSize(new Dimension(1000, 700));
    this.setPreferredSize(new Dimension(1000, 700));

    this.setLayout(new BorderLayout());


    UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
    try {
      UIManager.setLookAndFeel(looks[3].getClassName());
    } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException
        | InstantiationException e) {
      e.printStackTrace();
    }


    JPanel menu = new JPanel();
    menu.setPreferredSize(new Dimension(1000, 100));
    menu.setLayout(new GridLayout(1, 6));

    JButton buttonB = new JButton("random weight");
    buttonB.addActionListener(e -> {
      genome.mutateWeightRandom();
      repaint();
    });
    menu.add(buttonB);

    JButton buttonZ = new JButton("weight shift");
    buttonZ.addActionListener(e -> {
      genome.mutateWeightShift();
      repaint();
    });
    menu.add(buttonZ);

    JButton buttonC = new JButton("Link mutate");
    buttonC.addActionListener(e -> {
      genome.mutateLink();
      repaint();
    });
    menu.add(buttonC);

    JButton buttonD = new JButton("Node mutate");
    buttonD.addActionListener(e -> {
      genome.mutateNode();
      repaint();
    });
    menu.add(buttonD);


    JButton buttonE = new JButton("on/off");
    buttonE.addActionListener(e -> {
      genome.mutateLinkToggle();
      repaint();
    });
    menu.add(buttonE);

    JButton buttonF = new JButton("Mutate");
    buttonF.addActionListener(e -> {
      genome.mutate();
      repaint();
    });
    menu.add(buttonF);

    JButton buttonG = new JButton("Calculate");
    buttonG.addActionListener(e -> {
      genome.generate_calculator();
      System.out.println(Arrays.toString(genome.calculate(1, 1)));
      repaint();
    });
    menu.add(buttonG);


    this.add(menu, BorderLayout.NORTH);

    this.panel = new Panel();
    this.add(panel, BorderLayout.CENTER);

    this.setVisible(true);
  }

  public void setGenome(Genome genome) {
    panel.setGenome(genome);
    this.genome = genome;
  }

}