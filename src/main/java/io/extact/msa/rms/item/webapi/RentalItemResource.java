package io.extact.msa.rms.item.webapi;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirements;

import io.extact.msa.rms.item.webapi.dto.AddRentalItemEventDto;
import io.extact.msa.rms.item.webapi.dto.RentalItemResourceDto;
import io.extact.msa.rms.platform.fw.domain.constraint.RmsId;

public interface RentalItemResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //--- for OpenAPI
    @Operation(operationId = "getAll", summary = "レンタル品の全件を取得する", description = "登録されているすべてのレンタル品を取得する")
    @SecurityRequirements({@SecurityRequirement(name = "RmsHeaderAuthn"), @SecurityRequirement(name = "RmsHeaderAuthz")})
    @APIResponse(responseCode = "200", description = "検索結果", content = @Content(mediaType = "application/json", schema = @Schema(type = SchemaType.ARRAY, implementation = RentalItemResourceDto.class)))
    @APIResponse(responseCode = "500", ref = "#/components/responses/ServerError")
    @APIResponse(responseCode = "503", ref = "#/components/responses/ServiceUnavailable")
    List<RentalItemResourceDto> getAll();

    @GET
    @Path("/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    //--- for OpenAPI
    @Operation(operationId = "get", summary = "レンタル品を取得する", description = "指定されたレンタル品を取得する。なお、該当なしはnullに相当する204(NoContent)を返す")
    @SecurityRequirements({@SecurityRequirement(name = "RmsHeaderAuthn"), @SecurityRequirement(name = "RmsHeaderAuthz")})
    @Parameter(name = "itemId", description = "レンタル品ID", in = ParameterIn.PATH, required = true)
    @APIResponse(responseCode = "200", description = "レンタル品", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalItemResourceDto.class)))
    @APIResponse(responseCode = "204", ref = "#/components/responses/NoContent")
    @APIResponse(responseCode = "400", ref = "#/components/responses/ParameterError")
    @APIResponse(responseCode = "500", ref = "#/components/responses/ServerError")
    @APIResponse(responseCode = "503", ref = "#/components/responses/ServiceUnavailable")
    RentalItemResourceDto get(@RmsId @PathParam("itemId") Integer itemId);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //--- for OpenAPI
    @Operation(operationId = "add", summary = "レンタル品を登録する", description = "シリアル番号が既に使われている場合は409を返す")
    @SecurityRequirements({@SecurityRequirement(name = "RmsHeaderAuthn"), @SecurityRequirement(name = "RmsHeaderAuthz")})
    @Parameter(name = "dto", description = "登録内容", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddRentalItemEventDto.class)))
    @APIResponse(responseCode = "200", description = "登録成功", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddRentalItemEventDto.class)))
    @APIResponse(responseCode = "400", ref = "#/components/responses/ParameterError")
    @APIResponse(responseCode = "409", ref = "#/components/responses/DataDupricate")
    @APIResponse(responseCode = "500", ref = "#/components/responses/ServerError")
    RentalItemResourceDto add(@Valid AddRentalItemEventDto dto);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    //--- for OpenAPI
    @Operation(operationId = "update", summary = "レンタル品を更新する", description = "依頼されたレンタル品を更新する")
    @SecurityRequirements({@SecurityRequirement(name = "RmsHeaderAuthn"), @SecurityRequirement(name = "RmsHeaderAuthz")})
    @Parameter(name = "updateDto", description = "更新内容", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalItemResourceDto.class)))
    @APIResponse(responseCode = "200", description = "登録成功", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalItemResourceDto.class)))
    @APIResponse(responseCode = "400", ref = "#/components/responses/ParameterError")
    @APIResponse(responseCode = "404", ref = "#/components/responses/UnknownData")
    @APIResponse(responseCode = "500", ref = "#/components/responses/ServerError")
    @APIResponse(responseCode = "503", ref = "#/components/responses/ServiceUnavailable")
    RentalItemResourceDto update(@Valid RentalItemResourceDto dto);

    @DELETE
    @Path("/{itemId}")
    //--- for OpenAPI
    @Operation(operationId = "delete", summary = "レンタル品を削除する", description = "削除対象のレンタル品を参照する予約が存在する場合は削除は行わずエラーにする")
    @SecurityRequirements({@SecurityRequirement(name = "RmsHeaderAuthn"), @SecurityRequirement(name = "RmsHeaderAuthz")})
    @Parameter(name = "itemId", description = "レンタル品ID", in = ParameterIn.PATH, required = true)
    @APIResponse(responseCode = "200", description = "登録成功")
    @APIResponse(responseCode = "400", ref = "#/components/responses/ParameterError")
    @APIResponse(responseCode = "404", ref = "#/components/responses/UnknownData")
    @APIResponse(responseCode = "409", ref = "#/components/responses/DataRefered")
    @APIResponse(responseCode = "500", ref = "#/components/responses/ServerError")
    @APIResponse(responseCode = "503", ref = "#/components/responses/ServiceUnavailable")
    void delete(@RmsId @PathParam("itemId") Integer itemId);

    @GET
    @Path("/exists/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    //--- for OpenAPI
    @Operation(operationId = "exists", summary = "指定されたレンタル品が存在するかを返す")
    @SecurityRequirements({@SecurityRequirement(name = "RmsHeaderAuthn"), @SecurityRequirement(name = "RmsHeaderAuthz")})
    @Parameter(name = "itemId", description = "レンタル品ID", in = ParameterIn.PATH, required = true)
    @APIResponse(responseCode = "200", description = "ある場合はtrueを返す", content = @Content(mediaType = "application/json", schema = @Schema(type = SchemaType.BOOLEAN, implementation = Boolean.class)))
    @APIResponse(responseCode = "400", ref = "#/components/responses/ParameterError")
    @APIResponse(responseCode = "500", ref = "#/components/responses/ServerError")
    @APIResponse(responseCode = "503", ref = "#/components/responses/ServiceUnavailable")
    boolean exists(@RmsId @PathParam("itemId") Integer itemId);
}
