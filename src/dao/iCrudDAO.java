
package dao;

import java.util.List;

/**
 *
 * @author Eduardo
 */
public interface iCrudDAO<T> {
    int insertarProductoInventario(T entidad);
    int modificarProductoInventario(T entidad);
    int eliminarProductoInventario(int id);
    List<T> obtenerProductoInventario();
    T obtenerProductoInventarioPorId(int id);
}