$('span.glyphicon-file').click(function () {
    $.ajax({
        url: "filemanager-api/openFile?name=" + btoa($(this).attr("name")),
        success: function (result) {
            console.log(result);
        }
    });
});

$('a.file-edit').click(function () {
    let divFileEdit = $('div#fileEdit');
    divFileEdit.focus();
    var fileInfoRowId = $(this).attr("id").replace("edit_", "fileInfo_");
    var fileInfoRow = $.find("#" + fileInfoRowId);
    var fileId = $(fileInfoRow).find('td#' + fileInfoRowId + '_id').text();
    $('#dlgFileId').text(fileId);
    var filePath = $(fileInfoRow).find('td#' + fileInfoRowId + '_filePath').text();
    $('#dlgFileName').text(filePath);
    var fileTags = $(fileInfoRow).find('td#' + fileInfoRowId + '_tags').text()
    $('#dlgFileTags').text(fileTags);
    var fileDates = $(fileInfoRow).find('td#' + fileInfoRowId + '_keyDates').text();
    $('#dlgFileDates').text(fileDates);
});