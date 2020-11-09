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
public class ActionFrequencyFileModel {
    @ExcelCol(index = 0, type = ExcelColType.STRING)
    private String frequencyValue;
}
