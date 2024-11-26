$(document).ready(function(){
    //$('.dataTable > thead > tr').addClass('text-white bg-primary');
    $('#form_error_top').hide();
    $('.invalid-feedback').hide();
    $('#myform input[type=text]').keypress(function (event) {
        if (event.keyCode==13) {
            event.preventDefault();
            return false;
        }
    });
    $('#filter-form input[type=text]').keypress(function (event) {
        if (event.keyCode==13) {
            event.preventDefault();
            return false;
        }
    });
    $('#btn-submit').click(function() {
        submitForm();
    });
    $('#filter-ajax-btn').click(function() {
        let table = $(this).attr('data-table');
        submitFilter(table);
    });
    $('#btn-add').click(function() {
        let url = $(this).attr('data-url');
        let mode = $(this).attr('data-mode');
        let title = $(this).attr('data-title');
        console.log('url '+url+' mode '+mode+' title '+title);
        loadForm(url,mode,title,-1);
    });
    $('.btn-load-form').click(function() {
        let url = $(this).attr('data-url');
        let mode = $(this).attr('data-mode');
        let title = $(this).attr('data-title');
        let id = $(this).attr('data-id');

        let urlProcess = $(this).attr('data-url-process');

        console.log('url load '+url+' mode '+mode+' title '+title+' id '+id+' url process '+urlProcess);
        loadForm(url,mode,title,id,urlProcess);
    });
    $('.btn-update-delete').click(function() {
        let url = $(this).attr('data-url');
        let id = $(this).attr('data-id');
        let name = $(this).attr('data-name');
        let command = $(this).attr('data-command');
        let suffixAsIdentifier = $(this).attr('data-suffix');
        console.log('url '+url+' id '+id+' name '+name+' command '+command+' suffixAsIdentifier '+suffixAsIdentifier);
        updateOrDeleteRecord(url,id,name,command,suffixAsIdentifier);
    });
    $('.btn-do-update-delete').click(function() {
        let suffixAsIdentifier = $(this).attr('data-suffix');
        console.log('suffixAsIdentifier '+suffixAsIdentifier);
        doUpdateOrDeleteRecord(suffixAsIdentifier);
    });

    $(window).on('hidden.bs.modal', function() {
        $('.form-control').removeAttr('disabled');
        $('input[type=text]').removeAttr('disabled');
        $('.radio').removeAttr('disabled');
        $('.always-show').show();
    });
    $('#btn-submit').click(function () {
       submitForm();
    });
    $('.btn-clear-filter').click(function (){
        $('#filter-form input[type=text]:not(.avoid-clear), #filter-form select:not(.avoid-clear)').val('');
        $('#filter-ajax-btn').click();
    });
});

function submitFilter(table) {
    $('#filter-form input').attr('readonly','readonly');
    $('#filter-form select').attr('readonly','readonly');
    $('#filter-ajax-loader').show();
    $('#filter-ajax-btn').hide();
    var arr1 = $('#filter-form').serialize();
    $.post($('#filter_action').val(),arr1,function(resp) {
        if(resp.res){
            (table)? reloadDatatable(table) : window.location = location.href;
        }else{
            showToast('error','Error','Failed to filter data, please contact Administrator');
        }
        $('#filter-form input').removeAttr('readonly');
        $('#filter-form select').removeAttr('readonly');
        $('#filter-ajax-btn').show();
        $('#filter-ajax-loader').hide();
    });
}
function showToast(type, title, msg) {
    switch (type) {
        case "info":
            toastr.info(msg, title, { positionClass: 'toastr toast-top-right', containerId: 'toast-top-right', closeButton: true});
            break;
        case "error":
            toastr.error(msg, title, { positionClass: 'toastr toast-top-right', containerId: 'toast-top-right', closeButton: true});
            break;
        case "success":
            toastr.success(msg, title, { positionClass: 'toastr toast-top-right', containerId: 'toast-top-right', closeButton: true});
            break;
        case "warning":
            toastr.warning(msg, title, { positionClass: 'toastr toast-top-right', containerId: 'toast-top-right', closeButton: true});
            break;
    }
}

function loadForm(url,mode,title,id, urlProcess = "") {
    $('#btn-add').hide();
    $('#dropdown-'+id).hide();
    $('#ajax-loader-add').show();
    $('#ajax-loader-'+id).show();
    $('.alert').hide();
    let csrfToken = $("meta[name='_csrf']").attr("content");
    $.post(url,{id:id, mode:mode, _csrf:csrfToken},function(resp) {
        
        if(mode=='unblock'){
            updateOrDeleteRecord(urlProcess,id,"","","-unblock");
            $("#x-name-unblock").text(title)

            let modal = new bootstrap.Modal($('#modal-confirm-unblock'), {
                backdrop: 'static',
                keyboard: false
            })

            modal.show();
        }else if(mode=='block'){
            updateOrDeleteRecord(urlProcess,id,"","","-block");
            $("#x-name-block").text(title)

            let modal = new bootstrap.Modal($('#modal-confirm-block'), {
                backdrop: 'static',
                keyboard: false
            })

            modal.show();
        }else if(mode=='delete'){
            updateOrDeleteRecord(urlProcess,id,"","","");
            $("#x-name").text(title)

            let modal = new bootstrap.Modal($('#modal-confirm-danger'), {
                backdrop: 'static',
                keyboard: false
            })

            modal.show();
        }else if(mode=='release'){
            updateOrDeleteRecord(urlProcess,id,"","","-release");
            $("#x-name-release").text(title)

            let modal = new bootstrap.Modal($('#modal-confirm-release'), {
                backdrop: 'static',
                keyboard: false
            })

            modal.show();
        }else{
            $('#modal-title').html(title);
            $('#modal-body').html(resp);
            let modal = new bootstrap.Modal($('#modal'), {
                backdrop: 'static',
                keyboard: false
            })
            
            modal.show();
        }

        // $('#modal').modal({
        //     backdrop: 'static',
        //     keyboard: false
        // });
        $('#btn-add').show();
        $('#dropdown-'+id).show();
        $('#ajax-loader-add').hide();
        $('#ajax-loader-'+id).hide();
    }).fail(function(jqXHR, textStatus, errorThrown) {
        // You can also check the status code
        if(jqXHR.status == 401){
            window.location = location.href;
        }
    });
}
function formShow() {
    $('.form-control').attr('disabled','disabled');
    $('.form-select').attr('disabled','disabled');
    $('.nav.form-control').removeAttr('disabled');
    $('input[type=text]').attr('disabled','disabled');
    $('input[type=checkbox]').attr('disabled','disabled');
    $('.radio').attr('disabled','disabled');
    $('#btn-action').hide();
    $(".bootstrap-duallistbox-container").find(".btn-group").hide();
    $('.show-case').hide();

}
function formApprove() {
    $('#ajax-loader-add').hide();
    $('#ajax-loader-'+$('#ID_PEND').val()).hide();
    $('#dropdown-'+$('#ID_PEND').val()).show();
    $('.form-control').attr('disabled','disabled');
    $('.nav.form-control').removeAttr('disabled');
    $('input[type=text]').attr('disabled','disabled');
    $('input[type=checkbox]').attr('disabled','disabled');
    $('.radio').attr('disabled','disabled');
    $('#btn-action-approve').show();
    $('#btn-action').hide();
    $('.show-case').hide();
}
function rebindClick() { //tidak untuk dipasang di form
    $('.btn-load-form').unbind("click");
    $('.btn-load-form').click(function() {
        let url = $(this).attr('data-url');
        let mode = $(this).attr('data-mode');
        let title = $(this).attr('data-title');
        let id = $(this).attr('data-id');

        let urlProcess = $(this).attr('data-url-process');

        console.log('url load '+url+' mode '+mode+' title '+title+' id '+id+' url process '+urlProcess);

        loadForm(url,mode,title,id, urlProcess);
    });
    $('.btn-update-delete').unbind("click");
    $('.btn-update-delete').click(function() {
        let url = $(this).attr('data-url');
        let id = $(this).attr('data-id');
        let name = $(this).attr('data-name');
        let command = $(this).attr('data-command');
        let suffixAsIdentifier = $(this).attr('data-suffix');
        console.log('url '+url+' id '+id+' name '+name+' command '+command+' suffixAsIdentifier '+suffixAsIdentifier);
        updateOrDeleteRecord(url,id,name,command,suffixAsIdentifier);
    });
    $('#btn-submit').unbind("click");
    $('#btn-submit').click(function () {
        submitForm();
    });
    $('.btn-action-mode').unbind("click").click(function (){
        doActionMode(this);
    });
}
function submitForm() {
    doSubmitForm('#myform');
}
function doSubmitForm(formId) {
    var action = $('#action').val();
    $(formId+' input').attr('readonly','readonly');
    $(formId+' select').attr('readonly','readonly');
    $(formId+' .custom-control-input').attr('disabled','disabled');
    $('.disabled-on-edit').removeAttr('disabled');
    $('.alert').hide();
    $('#resp-err').html('');
    $('#ajax-loader').show();
    $('#ajax-btn').hide();
    $('.invalid-feedback').hide();
    $('.form-control').removeClass('is-invalid');

    $.post(action,$(formId).serialize(),function(resp) {
        if (resp.res) {
            window.location = location.href;
        } else {
            $(formId+' input').removeAttr('readonly');
            $(formId+' select').removeAttr('readonly');
            $(formId+' .custom-control-input').removeAttr('disabled');
            $('#ajax-btn').show();
            $('#ajax-loader').hide();
            var msg = '';
            var lastErrField = '';
            for (i=0;i<resp.msg.length;i++)
                msg += resp.msg[i]+'<br />';
            if (msg.length>0) {
                $('#form_error_top').show();
                $('#resp-err').html(msg);
            }
            for (i=0;i<resp.fields.length;i++) {
                $('#input_'+resp.fields[i].field).addClass('is-invalid');
                $('#error_'+resp.fields[i].field).html(resp.fields[i].msg);
                $('#error_'+resp.fields[i].field).fadeIn(300);
                lastErrField = '#error_'+resp.fields[i].field;
            }
            showErrorTab(lastErrField);
        }
    }).fail(function(jqXHR, textStatus, errorThrown) {
        // You can also check the status code
        if(jqXHR.status == 401){
            window.location = location.href;
        }
        
        $('#btn-add').show();
        $('#dropdown-'+id).show();
        $('#ajax-loader-add').hide();
        $('#ajax-loader-'+id).hide();
    });
}
function submitList() {
    doSubmitList('#myform');
}
function doSubmitList(formId) {
    var action = $('#action').val();
    $(formId+' input').attr('readonly','readonly');
    $(formId+' select').attr('readonly','readonly');
    $('.disabled-on-edit').removeAttr('disabled');
    $('.alert').hide();
    $('#ajax-loader').show();
    $('#ajax-btn').hide();

    $.post(action,$(formId).serialize(),function(resp) {
        if (resp.res) {
            window.location = location.href;
        } else {
            
            console.log(resp)
            $(formId+' input').removeAttr('readonly');
            $(formId+' select').removeAttr('readonly');
            $(formId+' .custom-control-input').removeAttr('disabled');
            $('#ajax-btn').show();
            $('#ajax-loader').hide();
            var lastErrField = '';

            for (i=0;i<resp.fields.length;i++) {
                $('#'+resp.fields[i].field).addClass('is-invalid');
                $('#input_'+resp.fields[i].field).addClass('is-invalid');
                $('#error_'+resp.fields[i].field).html(resp.fields[i].msg);
                $('#error_'+resp.fields[i].field).fadeIn(300);
                lastErrField = '#error_'+resp.fields[i].field;
            }
        }

        $(formId+' input').removeAttr('readonly');
        $(formId+' select').removeAttr('readonly');
        $('#ajax-btn').show();
        $('#ajax-loader').hide();
    }).fail(function(jqXHR, textStatus, errorThrown) {
        // You can also check the status code
        if(jqXHR.status == 401){
            window.location = location.href;
        }
        
        // $('#btn-add').show();
        // $('#dropdown-'+id).show();
        // $('#ajax-loader-add').hide();
        // $('#ajax-loader-'+id).hide();
    });

}

function updateOrDeleteRecord(url,id,name,command,suffixAsIdentifier) {
    $('#x-url'+suffixAsIdentifier).val(url);
    $('#x-id'+suffixAsIdentifier).val(id);
    $('#x-name'+suffixAsIdentifier).html(name);
    $('#x-command'+suffixAsIdentifier).val(command);
    $('.alert').hide();
}

function doUpdateOrDeleteRecord(suffixAsIdentifier) {
    $('.alert').hide();
    $('#ajax-loader'+suffixAsIdentifier).show();
    $('#ajax-btn'+suffixAsIdentifier).hide();
    let csrfToken = $("meta[name='_csrf']").attr("content");
    $.post($('#x-url'+suffixAsIdentifier).val(),{id:$('#x-id'+suffixAsIdentifier).val(),name:$('#x-name'+suffixAsIdentifier).html(),_csrf:csrfToken},function(resp){
        if (resp.res) {
            window.location = location.href;
        }else {
            $('#ajax-btn'+suffixAsIdentifier).show();
            $('#ajax-loader'+suffixAsIdentifier).hide();
            var msg = '';
            for (i=0;i<resp.msg.length;i++)
                msg += resp.msg[i]+'<br />';
            if (msg.length>0) {
                $('#form_error_secondary_top').show();
                $('#resp-err-secondary').html(msg);
            }
        }
    }).fail(function(jqXHR, textStatus, errorThrown) {
        // You can also check the status code
        if(jqXHR.status == 401){
            window.location = location.href;
        }
        
        $('#btn-add').show();
        $('#dropdown-'+id).show();
        $('#ajax-loader-add').hide();
        $('#ajax-loader-'+id).hide();
    });
}
function submitPendingTask(mode){
    var idPend = $('#myform [name=ID_PEND]').val();
    var url;
    if(mode === 'approve')
        url = $('#url_approve').val();
    else
        url = $('#url_reject').val();
    var csrfToken = $("meta[name='_csrf']").attr("content");
    $('#ajax-btn-approve').hide();
    $('#ajax-loader-approve').show();
    $('.alert').hide();
    $.post(url,{idPend:idPend, _csrf:csrfToken}, function(resp) {
        if (resp.res===true) {
            window.location = location.href;
        } else {
            $('#ajax-btn').show();
            $('#ajax-loader').hide();
            var msg = '';
            for (i=0;i<resp.msg.length;i++)
                msg += resp.msg[i]+'<br />';
            if (msg.length>0) {
                $('#form_error_top').show();
                $('#resp-err').html(msg);
            }
            for (i=0;i<resp.fields.length;i++) {
                $('#input_'+resp.fields[i].field).addClass('is-invalid');
                $('#error_'+resp.fields[i].field).html(resp.fields[i].msg);
                $('#error_'+resp.fields[i].field).fadeIn(300);
            }
            $('#ajax-btn-approve').show();
            $('#ajax-loader-approve').hide();
        }
    }).fail(function(jqXHR, textStatus, errorThrown) {
        // You can also check the status code
        if(jqXHR.status == 401){
            window.location = location.href;
        }
        
        $('#btn-add').show();
        $('#dropdown-'+id).show();
        $('#ajax-loader-add').hide();
        $('#ajax-loader-'+id).hide();
    });;
}
function doActionMode(that){
    let url = $(that).attr('data-url');
    let id = $(that).attr('data-id');
    let name = $(that).attr('data-name');
    let mode = $(that).attr('data-mode');
    let table = $(that).attr('data-table');
    let csrfToken = $("meta[name='_csrf']").attr("content");

    const swalBootstrap = Swal.mixin({
        customClass: {confirmButton: 'btn btn-sm btn-info btn-action', cancelButton: 'mr-1 btn btn-sm btn-secondary btn-action'},
        buttonsStyling: false,
    })
    swalBootstrap.fire({
        title: capitalize(mode)+' MT Report', text: capitalize(mode)+" the MT report generation?", icon: 'question',
        showCancelButton: true,
        width: 450,
        confirmButtonText: capitalize(mode),
        reverseButtons: true,
        allowOutsideClick: false
    }).then((result) => {
        if (result.value) {
            $.ajax({
                type: "POST",
                url: url + '/' + mode,
                data: {id:id, name:name, _csrf:csrfToken},
                beforeSend: function () {
                    Swal.fire({
                        title: 'Loading',
                        html:'<span class="spinner-border text-inverse"></span>',
                        showConfirmButton: false,
                        allowOutsideClick: false,
                        width: 450
                    });
                }
            }).done(function(resp){
                if(resp.status){
                    if(resp.responseCode === '00'){
                        swalBootstrap.fire('Success', 'Successfully '+ mode +' MT report for ' + name, 'success');
                        reloadDatatable(table);
                    }else
                        swalBootstrap.fire('Failed', resp.responseMessage, 'warning');
                }else
                    swalBootstrap.fire('Exception', 'Action failed, [' + resp.responseCode + '] ' + resp.responseMessage , 'error');
            }).fail(function(jqXHR, textStatus, errorThrown){
                swalBootstrap.fire('Exception', 'Action failed, please try again later', 'error');

                if(jqXHR.status == 401){
                    window.location = location.href;
                }
            });
        }
    })
}
function currencyFormat(strCcy){
    if(null == strCcy || strCcy.length <= 0)
        return "";
    return parseFloat(strCcy).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
}
function bindNumberOnlyInputText(){
    $('.number-only').unbind('keypress').keypress(function (e) {
        var charCode = (e.which) ? e.which : event.keyCode
        if (String.fromCharCode(charCode).match(/[^0-9]/g))
            return false;
    });
    $('.number-digit-only').unbind('keypress').keypress(function (e) {
        return isNumberKey(this, e);
    });
}
function reloadDatatable(table){
    $(table).DataTable().ajax.reload(function () {
        rebindClick();
    });
}
function capitalize(word) {
    return word[0].toUpperCase() + word.slice(1).toLowerCase();
}
function showErrorTab(errField){
    let that = $(errField);
    while($(that).parents(".tab-pane").length){
        $('a[href="#' + $(that).parents(".tab-pane").attr('id') + '"]').tab('show');
        that = $(that).parents(".tab-pane");
    }
}
function isNumberKey(txt, evt) {
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode === 46) {
        return txt.value.indexOf('.') === -1;
    } else {
        if (charCode > 31 && (charCode < 48 || charCode > 57))
            return false;
    }
    return true;
}