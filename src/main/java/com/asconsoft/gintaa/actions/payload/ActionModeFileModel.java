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
public class ActionModeFileModel {
    @ExcelCol(index = 0, type = ExcelColType.STRING)
    private String name;
    @ExcelCol(index = 1, type = ExcelColType.STRING)
    private String description;
}
