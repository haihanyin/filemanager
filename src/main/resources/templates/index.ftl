<html>
<head>
    <meta charset="UTF-8">
    <title>Haihan's File Manager</title>
    <link rel="stylesheet" href="/webjars/bootstrap/4.5.1/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/webjars/bootstrap-glyphicons/bdd2cbfba0/css/bootstrap-glyphicons.css"/>
    <script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/4.5.1/js/bootstrap.min.js"></script>
</head>
<body>
<table>
    <thead class="table">
    <tr>
        <th scope="col">Id</th>
        <th scope="col">File Path</th>
        <th scope="col">Tags</th>
        <th scope="col">Key Dates</th>
        <th scope="col">Open</th>
        <th scope="col">Edit</th>
    </tr>
    </thead>
    <tbody>
    <#list fileInfoDtoList as fileInfoDto>
        <tr id="fileInfo_${fileInfoDto.id}" style="line-height: 30px; min-height: 30px; height: 30px;">
            <td id="fileInfo_${fileInfoDto.id}_id">${fileInfoDto.id}</td>
            <td id="fileInfo_${fileInfoDto.id}_filePath">${fileInfoDto.filePath}</td>
            <td id="fileInfo_${fileInfoDto.id}_tags">${fileInfoDto.tags}</td>
            <td id="fileInfo_${fileInfoDto.id}_keyDates">${fileInfoDto.keyDates}</td>
            <td>
                <span class="align-middle glyphicon glyphicon-file" name="${fileInfoDto.filePath}"></span>
            </td>
            <td>
                <a class="file-edit" data-toggle="modal" data-target="#exampleModal" id="edit_${fileInfoDto.id}">
                    <span class="align-middle glyphicon glyphicon-edit" name="${fileInfoDto.filePath}"></span>
                </a>
            </td>
        </tr>
    </#list>
    </tbody>
</table>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label>File Id</label>
                        <label id="dlgFileId">12345</label>
                    </div>
                    <div class="form-group">
                        <label>File Name</label>
                        <label id="dlgFileName">File Name</label>
                    </div>
                    <div class="form-group">
                        <label for="fileTags">Tags</label>
                        <textarea type="text" rows="3" class="form-control" id="dlgFileTags" placeholder="tags"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="keyDates">Key dates</label>
                        <input type="text" class="form-control" id="dlgFileDates" placeholder="2020-09-14">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div>
    </div>
</div>
<script src="/static/js/filemanager.js"></script>
</body>
</html>