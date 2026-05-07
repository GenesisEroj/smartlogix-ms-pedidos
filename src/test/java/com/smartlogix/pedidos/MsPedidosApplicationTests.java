package com.smartlogix.pedidos;

import com.smartlogix.pedidos.model.Pedido;
import com.smartlogix.pedidos.repository.PedidoRepository;
import com.smartlogix.pedidos.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MsPedidosApplicationTests {

	@Mock
	private PedidoRepository pedidoRepository;

	@InjectMocks
	private PedidoService pedidoService;

	private Pedido pedido;

	@BeforeEach
	void setUp() {
		pedido = Pedido.builder()
				.id(1L)
				.clienteId("cliente-001")
				.productoId("producto-001")
				.cantidad(5)
				.estado("PENDIENTE")
				.build();
	}

	@Test
	void crearPedido_debeRetornarPedidoGuardado() {
		when(pedidoRepository.save(pedido)).thenReturn(pedido);
		Pedido resultado = pedidoService.crearPedido(pedido);
		assertNotNull(resultado);
		assertEquals("cliente-001", resultado.getClienteId());
		verify(pedidoRepository, times(1)).save(pedido);
	}

	@Test
	void obtenerTodos_debeRetornarListaDePedidos() {
		when(pedidoRepository.findAll()).thenReturn(Arrays.asList(pedido));
		List<Pedido> resultado = pedidoService.obtenerTodos();
		assertFalse(resultado.isEmpty());
		assertEquals(1, resultado.size());
		verify(pedidoRepository, times(1)).findAll();
	}

	@Test
	void obtenerPorId_debeRetornarPedido() {
		when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
		Pedido resultado = pedidoService.obtenerPorId(1L);
		assertNotNull(resultado);
		assertEquals(1L, resultado.getId());
	}

	@Test
	void obtenerPorId_debeArrojarExcepcionSiNoExiste() {
		when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());
		assertThrows(RuntimeException.class, () -> pedidoService.obtenerPorId(99L));
	}

	@Test
	void actualizarEstado_debeActualizarEstadoDelPedido() {
		when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
		when(pedidoRepository.save(pedido)).thenReturn(pedido);
		Pedido resultado = pedidoService.actualizarEstado(1L, "ENVIADO");
		assertEquals("ENVIADO", resultado.getEstado());
		verify(pedidoRepository, times(1)).save(pedido);
	}
}