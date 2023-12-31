/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package client.GUI;

import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Adam
 */
public class ImageChooser extends javax.swing.JFrame {

    /**
     * Creates new form AvatarChooser
     */
    StringBuilder outputPath;
    JLabel pathLable;

    public ImageChooser(StringBuilder outputPath) {
        this.outputPath = outputPath;
        initComponents();
        imageChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png"));
    }

    public ImageChooser(StringBuilder outputPath, JLabel pathLable) {
        this.outputPath = outputPath;
        initComponents();
        imageChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png"));
        this.pathLable = pathLable;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        imageChooser = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        imageChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avatarChooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(imageChooser, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(imageChooser, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)));

        pack();
    }// </editor-fold>

    private void avatarChooserActionPerformed(java.awt.event.ActionEvent evt) {
        String returnVal = evt.getActionCommand();
        if (returnVal.equals(this.imageChooser.CANCEL_SELECTION)) {
            this.dispose();
            return;
        }
        String path = imageChooser.getSelectedFile().getAbsolutePath();
        if (returnVal.equals(this.imageChooser.APPROVE_SELECTION)
                && (path.substring(path.lastIndexOf("."), path.length()).equals(".png")
                        || path.substring(path.lastIndexOf("."), path.length()).equals(".jpg"))) {
            outputPath.delete(0, outputPath.length());
            outputPath.append(path);
            if (pathLable != null) {
                pathLable
                        .setText(outputPath.substring(outputPath.lastIndexOf("\\") + 1, outputPath.length()));
            }
            this.dispose();
        }
    }

    // Variables declaration - do not modify
    private javax.swing.JFileChooser imageChooser;
    // End of variables declaration
}
