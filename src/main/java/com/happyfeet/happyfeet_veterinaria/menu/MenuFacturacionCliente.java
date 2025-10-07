/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.happyfeet.happyfeet_veterinaria.menu;

import com.happyfeet.dao.*;
import com.happyfeet.modelo.*;

import javax.swing.*;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MenuFacturacionCliente {

    public static void mostrar(Connection con) {
        FacturaDAO facturaDAO = new FacturaDAO(con);
        ItemFacturaDAO itemFacturaDAO = new ItemFacturaDAO(con);
        InventarioDAO inventarioDAO = new InventarioDAO(con);
        DuenoDAO duenoDAO = new DuenoDAO(con);

        String opcion;
        do {
            opcion = JOptionPane.showInputDialog("""
                    MENÚ FACTURA
                    1. Listar facturas
                    0. Volver
                    Seleccione una opción:""");

            if (opcion == null) {
                return;
            }

            switch (opcion) {
                case "1" ->
                    listar(facturaDAO, itemFacturaDAO, inventarioDAO, duenoDAO);
                case "0" -> {
                }
                default ->
                    JOptionPane.showMessageDialog(null, "⚠️ Opción inválida.");
            }
        } while (!"0".equals(opcion));
    }

    private static void listar(FacturaDAO facturaDAO, ItemFacturaDAO itemFacturaDAO, InventarioDAO inventarioDAO,
            DuenoDAO duenoDAO) {

        try {
            String idStr = JOptionPane.showInputDialog("Ingrese el ID del cliente:");
            if (idStr == null || idStr.isBlank()) {
                return;
            }
            
            double total = 0;
            int clienteId = Integer.parseInt(idStr);

            Dueno dueno = duenoDAO.buscarPorId(clienteId);
            if (dueno == null) {
                JOptionPane.showMessageDialog(null, "⚠No existe un cliente con ese ID.");
                return;
            }

            StringBuilder sb = new StringBuilder("LISTADO DE FACTURAS\n");
            for (Factura f : facturaDAO.listarTodos()) {
                if (f.getDuenoId() == clienteId) {
                    Dueno d = duenoDAO.buscarPorId(f.getDuenoId());

                    sb.append("Factura ID: ").append(f.getId())
                            .append(" | Cliente: ").append(d != null ? d.getNombreCompleto() : "Desconocido")
                            .append(" | Fecha: ").append(f.getFechaEmision())
                            .append(" | Total: ").append(f.getTotal()).append("\n");
                    
                    total += f.getTotal();

                    for (ItemFactura item : itemFacturaDAO.listarPorFactura(f.getId())) {
                        Inventario prod = inventarioDAO.buscarPorId(item.getId());
                        sb.append("   -> ").append(prod != null ? prod.getNombreProducto() : "Producto desconocido")
                                .append(" | Cantidad: ").append(item.getCantidad())
                                .append(" | Precio U: ").append(item.getPrecioUnitario())
                                .append("\n");
                    }
                    sb.append("\n");
                    sb.append("Total consumido por el cliente: ").append(total);
                }
            }
            JOptionPane.showMessageDialog(null, sb.toString());

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar: " + e.getMessage());
        }
    }
}
