package com.smartlogix.pedidos.service;

import com.smartlogix.pedidos.model.Pedido;
import com.smartlogix.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    // Factory Method Pattern - crea pedidos según el tipo
    public Pedido crearPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    // CQRS - Query
    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    // CQRS - Query
    public Pedido obtenerPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));
    }

    // CQRS - Query
    public List<Pedido> obtenerPorCliente(String clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    // CQRS - Command
    public Pedido actualizarEstado(Long id, String nuevoEstado) {
        Pedido pedido = obtenerPorId(id);
        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }

    // CQRS - Command
    public void eliminarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }
}