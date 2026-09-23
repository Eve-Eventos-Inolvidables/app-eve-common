package com.example.appevecommon.Controller;

import com.example.appevecommon.Models.Base.BaseEntity;
import com.example.appevecommon.Service.AbstractBaseService;
import com.example.appevecommon.Service.Exception.ResourceNotFoundException;
import com.example.appevecommon.Service.Utilities.PagedFilter;
import com.example.appevecommon.Service.Utilities.Responses.PageResult;
import com.example.appevecommon.Service.Utilities.Responses.Response;
import com.example.appevecommon.Service.Utilities.Responses.ResponseFactory;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public abstract class BaseController<E extends BaseEntity,
        D,
        F extends PagedFilter,
        S extends AbstractBaseService<E, D, F>> {

    protected final S service;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected BaseController(S service) {
        this.service = service;
    }

    @PostMapping
    public Response<D> create(@Valid @RequestBody D dto) {
        return ResponseFactory.ok("Recurso creado exitosamente", service.create(dto));
    }

    @GetMapping("/{id}")
    public Response<D> getById(@PathVariable Long id) {
        return ResponseFactory.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public Response<D> update(@PathVariable Long id, @Valid @RequestBody D dto) {
        return ResponseFactory.ok("Recurso actualizado exitosamente", service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable Long id) {
        if (!service.delete(id)) {
            throw new ResourceNotFoundException(id);
        }
        return ResponseFactory.ok("Recurso eliminado exitosamente", null);
    }

    @GetMapping
    public Response<List<D>> getAll() {
        return ResponseFactory.ok(service.getAll());
    }

    @GetMapping("/paged")
    public Response<PageResult<D>> getByFilter(@ModelAttribute F filter) {
        return ResponseFactory.ok(service.getByFilter(filter));
    }
}