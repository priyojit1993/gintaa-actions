package com.asconsoft.gintaa.actions.payload;

import com.asconsoft.gintaa.utils.excel.ExcelCol;
import com.asconsoft.gintaa.utils.excel.ExcelColMapping;
import com.asconsoft.gintaa.utils.excel.ExcelColType;
import lombok.*;

@ExcelColMapping
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ActionsFileModel {
    @ExcelCol(index = 0, type = ExcelColType.STRING)
    private String actionLabel;
    @ExcelCol(index = 1, type = ExcelColType.STRING)
    private String actionUrl;
    @ExcelCol(index = 2, type = ExcelColType.STRING)
    private String actionPurpose;
    @ExcelCol(index = 3, type = ExcelColType.STRING)
    private String actionGroup;
    @ExcelCol(index = 4, type = ExcelColType.STRING)
    private String actionMode;
}
