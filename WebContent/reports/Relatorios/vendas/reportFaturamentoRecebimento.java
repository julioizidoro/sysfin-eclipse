<?xml version="1.0" encoding="UTF-8"?>
<jasperReport xmlns="http://jasperreports.sourceforge.net/jasperreports" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd" name="Mapa de Vendas Gerencial" pageWidth="595" pageHeight="842" columnWidth="555" leftMargin="20" rightMargin="20" topMargin="20" bottomMargin="20" uuid="320dd56b-4281-455e-becf-bdbfae77892b">
	<property name="ireport.zoom" value="1.0"/>
	<property name="ireport.x" value="0"/>
	<property name="ireport.y" value="54"/>
	<style name="Title" fontName="Arial" fontSize="26" isBold="true" pdfFontName="Helvetica-Bold"/>
	<style name="SubTitle" forecolor="#666666" fontName="Arial" fontSize="18"/>
	<style name="Column header" forecolor="#666666" fontName="Arial" fontSize="12" isBold="true"/>
	<style name="Detail" fontName="Arial" fontSize="12"/>
	<parameter name="dataInicial" class="java.util.Date">
		<defaultValueExpression><![CDATA[]]></defaultValueExpression>
	</parameter>
	<parameter name="dataFinal" class="java.util.Date"/>
	<parameter name="logo" class="java.awt.Image"/>
	<parameter name="sql" class="java.lang.String">
		<defaultValueExpression><![CDATA[]]></defaultValueExpression>
	</parameter>
	<parameter name="data" class="java.lang.Integer"/>
	<parameter name="data2" class="java.lang.String"/>
	<parameter name="data3" class="java.lang.String"/>
	<parameter name="data4" class="java.lang.String"/>
	<queryString>
		<![CDATA[$P!{sql}]]>
	</queryString>
	<field name="dataVenda" class="java.sql.Date">
		<fieldDescription><![CDATA[]]></fieldDescription>
	</field>
	<field name="liquidoVendas" class="java.lang.Float">
		<fieldDescription><![CDATA[]]></fieldDescription>
	</field>
	<field name="valorParcela" class="java.lang.Float">
		<fieldDescription><![CDATA[]]></fieldDescription>
	</field>
	<field name="nomeFantasia" class="java.lang.String">
		<fieldDescription><![CDATA[]]></fieldDescription>
	</field>
	<field name="valorBruto" class="java.lang.Float"/>
	<field name="idvendas" class="java.lang.Integer">
		<fieldDescription><![CDATA[]]></fieldDescription>
	</field>
	<field name="dataVencimento" class="java.sql.Date">
		<fieldDescription><![CDATA[]]></fieldDescription>
	</field>
	<field name="numeroParcela" class="java.lang.String"/>
	<background>
		<band splitType="Stretch"/>
	</background>
	<title>
		<band height="203" splitType="Stretch">
			<textField>
				<reportElement style="Detail" x="3" y="108" width="553" height="33" uuid="8b407713-f5f1-44b0-9223-250f03a69f01"/>
				<textElement textAlignment="Center">
					<font size="26" isBold="true"/>
				</textElement>
				<textFieldExpression><![CDATA["Faturamento X Recebimento"]]></textFieldExpression>
			</textField>
			<textField pattern="dd/MM/yyyy">
				<reportElement x="90" y="172" width="90" height="20" uuid="9adce713-8e13-4466-8f00-23d351c3aef0"/>
				<textElement>
					<font size="12"/>
				</textElement>
				<textFieldExpression><![CDATA[$P{dataInicial}]]></textFieldExpression>
			</textField>
			<textField>
				<reportElement x="4" y="172" width="71" height="20" uuid="6fd811b8-10d0-443a-ac2e-a687af719c6a"/>
				<textElement>
					<font size="12"/>
				</textElement>
				<textFieldExpression><![CDATA["Período : "]]></textFieldExpression>
			</textField>
			<textField pattern="dd/MM/yyyy">
				<reportElement x="222" y="172" width="90" height="20" uuid="1cce3309-e1d1-44a9-a918-d658b1e915d2"/>
				<textElement>
					<font size="12"/>
				</textElement>
				<textFieldExpression><![CDATA[$P{dataFinal}]]></textFieldExpression>
			</textField>
			<image>
				<reportElement x="10" y="10" width="255" height="94" uuid="4e2916c0-3c7e-4cbb-b349-67c7cfc09527"/>
				<imageExpression><![CDATA[$P{logo}]]></imageExpression>
			</image>
			<textField>
				<reportElement x="6" y="147" width="71" height="20" uuid="301daa18-f9e0-4bc1-9318-13fab4a0b9ac"/>
				<textElement>
					<font size="12"/>
				</textElement>
				<textFieldExpression><![CDATA["Unidade : "]]></textFieldExpression>
			</textField>
			<textField pattern="dd/MM/yyyy">
				<reportElement x="90" y="147" width="246" height="20" uuid="69d69f0c-05bc-45a7-9685-4bb46330d646"/>
				<textElement>
					<font size="12"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{nomeFantasia}]]></textFieldExpression>
			</textField>
			<textField pattern="dd/MM/yyyy">
				<reportElement style="Detail" x="491" y="-5" width="69" height="15" uuid="35706f80-5aa0-48ea-9713-ac9bbaff2af8"/>
				<textElement textAlignment="Center">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$P{data2}]]></textFieldExpression>
			</textField>
		</band>
	</title>
	<pageHeader>
		<band splitType="Stretch"/>
	</pageHeader>
	<columnHeader>
		<band height="24" splitType="Stretch">
			<line>
				<reportElement positionType="FixRelativeToBottom" x="0" y="21" width="555" height="1" uuid="b19ecc7b-3fa3-4530-9128-2103a4136dbc"/>
				<graphicElement>
					<pen lineWidth="0.5" lineColor="#999999"/>
				</graphicElement>
			</line>
			<textField pattern="dd/MM/yyyy">
				<reportElement style="Detail" x="295" y="5" width="69" height="15" uuid="667974b6-3c82-4a78-88c8-34dfd499f5ce"/>
				<textElement textAlignment="Center">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$P{data2}]]></textFieldExpression>
			</textField>
			<textField pattern="dd/MM/yyyy">
				<reportElement style="Detail" x="183" y="6" width="69" height="15" uuid="c9d17af2-ae21-478b-b5bf-38e6f92215aa"/>
				<textElement textAlignment="Center">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$P{data}]]></textFieldExpression>
			</textField>
			<textField pattern="dd/MM/yyyy">
				<reportElement style="Detail" x="389" y="5" width="69" height="15" uuid="b99c9a80-31a4-4f3c-b79c-c0f2578911dc"/>
				<textElement textAlignment="Center">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$P{data3}]]></textFieldExpression>
			</textField>
			<textField pattern="dd/MM/yyyy">
				<reportElement style="Detail" x="479" y="5" width="69" height="15" uuid="b44e0531-90d5-4256-b62e-a64a6e293376"/>
				<textElement textAlignment="Center">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$P{data4}]]></textFieldExpression>
			</textField>
		</band>
	</columnHeader>
	<detail>
		<band height="79" splitType="Stretch">
			<textField pattern="#,##0.00">
				<reportElement style="Detail" x="183" y="4" width="69" height="15" uuid="4c0b5781-b7bb-4213-a971-00ea0f01211d"/>
				<textElement textAlignment="Right">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{liquidoVendas}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00" isBlankWhenNull="false">
				<reportElement style="Detail" x="389" y="4" width="69" height="15" uuid="4d77b402-de9a-4751-8969-4aa056573073"/>
				<textElement textAlignment="Right">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{valorParcela}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00">
				<reportElement style="Detail" x="295" y="4" width="69" height="15" uuid="d4edab43-c0ec-430f-8014-e493b1ff6bcc"/>
				<textElement textAlignment="Right">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{dataVencimento}]]></textFieldExpression>
			</textField>
			<textField>
				<reportElement x="4" y="2" width="99" height="20" uuid="c4f6ffef-e1e2-4c19-bbdd-33bfe13beced"/>
				<textElement>
					<font size="12"/>
				</textElement>
				<textFieldExpression><![CDATA["Faturamento : "]]></textFieldExpression>
			</textField>
			<textField>
				<reportElement x="4" y="28" width="146" height="20" uuid="a249c53e-6cc8-4582-87df-1401aa5c20e8"/>
				<textElement>
					<font size="12"/>
				</textElement>
				<textFieldExpression><![CDATA[" Total Recebido no mês: "]]></textFieldExpression>
			</textField>
			<textField>
				<reportElement x="4" y="59" width="111" height="20" uuid="ffa30d3c-c9ed-422e-b0b4-30eacdb56ba6"/>
				<textElement>
					<font size="12"/>
				</textElement>
				<textFieldExpression><![CDATA["Total em aberto : "]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00" isBlankWhenNull="false">
				<reportElement style="Detail" x="389" y="31" width="69" height="15" uuid="69dbe62b-f115-46e8-9a09-57decc7cb915"/>
				<textElement textAlignment="Right">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{valorParcela}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00">
				<reportElement style="Detail" x="295" y="31" width="69" height="15" uuid="2be9bf38-2efb-4299-84ad-e1c711172208"/>
				<textElement textAlignment="Right">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{dataVencimento}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00">
				<reportElement style="Detail" x="183" y="31" width="69" height="15" uuid="054d3b44-8105-4fff-999d-83c7c1300409"/>
				<textElement textAlignment="Right">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{liquidoVendas}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00" isBlankWhenNull="false">
				<reportElement style="Detail" x="389" y="59" width="69" height="15" uuid="29dbc04f-ae05-4995-8912-b63d8a1ccb77"/>
				<textElement textAlignment="Right">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{valorParcela}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00">
				<reportElement style="Detail" x="295" y="59" width="69" height="15" uuid="dca63a8a-6cdc-491b-bd32-cf61281184d5"/>
				<textElement textAlignment="Right">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{dataVencimento}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00">
				<reportElement style="Detail" x="183" y="59" width="69" height="15" uuid="08ff8864-2519-4338-b245-51d8f668ab5f"/>
				<textElement textAlignment="Right">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{liquidoVendas}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00" isBlankWhenNull="false">
				<reportElement style="Detail" x="479" y="4" width="69" height="15" uuid="ee1b0fcc-36bc-43bd-b2a3-d566aefd7017"/>
				<textElement textAlignment="Right">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{valorParcela}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00" isBlankWhenNull="false">
				<reportElement style="Detail" x="479" y="31" width="69" height="15" uuid="e9338b8a-6c08-4b22-9b78-485d66f15112"/>
				<textElement textAlignment="Right">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{valorParcela}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00" isBlankWhenNull="false">
				<reportElement style="Detail" x="479" y="59" width="69" height="15" uuid="187b185e-a0c3-4cdb-9890-2351dc90517e"/>
				<textElement textAlignment="Right">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{valorParcela}]]></textFieldExpression>
			</textField>
		</band>
	</detail>
	<lastPageFooter>
		<band height="51">
			<textField evaluationTime="Report">
				<reportElement style="Column header" x="512" y="27" width="40" height="20" uuid="8a70a895-efc0-4d2d-a0d3-c0da62bc00ea"/>
				<textElement>
					<font size="10" isBold="false"/>
				</textElement>
				<textFieldExpression><![CDATA[" " + $V{PAGE_NUMBER}]]></textFieldExpression>
			</textField>
			<textField pattern="EEEEE dd MMMMM yyyy">
				<reportElement style="Column header" x="4" y="27" width="197" height="20" uuid="e7648991-7629-46f3-8634-3e60c0e83f52"/>
				<textElement>
					<font size="10" isBold="false"/>
				</textElement>
				<textFieldExpression><![CDATA[new java.util.Date()]]></textFieldExpression>
			</textField>
			<textField>
				<reportElement style="Column header" x="432" y="27" width="80" height="20" uuid="7af47d2a-817a-4af2-ba07-32ba475ab194"/>
				<textElement textAlignment="Right">
					<font size="10" isBold="false"/>
				</textElement>
				<textFieldExpression><![CDATA["Page "+$V{PAGE_NUMBER}+" of"]]></textFieldExpression>
			</textField>
		</band>
	</lastPageFooter>
</jasperReport>
