package com.example.appevecommon.Controller;

import com.example.appevecommon.Models.Base.BaseEntity;
import com.example.appevecommon.Service.Exception.ResourceNotFoundException;
import com.example.appevecommon.Service.IBaseService;
import com.example.appevecommon.Service.Utilities.Responses.PagedFilter;
import com.example.appevecommon.Service.Utilities.Responses.Ok.PagedResponse;
import com.example.appevecommon.Service.Utilities.Responses.Response;
import com.example.appevecommon.Service.Utilities.Responses.ResponseFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;


public abstract class BaseController<E extends BaseEntity,D, F extends PagedFilter, S extends IBaseService<E,D,F>> {

    protected final S service;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected BaseController(S service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Response<D> getById(@PathVariable Long id) {
        return ResponseFactory.ok(service.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!service.delete(id)) {
            throw new ResourceNotFoundException(id);
        }
        return ResponseFactory.noContent();
    }

    @GetMapping
    public PagedResponse<D> getByFilter(@ModelAttribute F filter) {
        return ResponseFactory.ok(service.getByFilter(filter));
    }
}