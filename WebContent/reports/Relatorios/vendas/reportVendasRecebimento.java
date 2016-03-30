<?xml version="1.0" encoding="UTF-8"?>
<jasperReport xmlns="http://jasperreports.sourceforge.net/jasperreports" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd" name="Mapa de Vendas Gerencial" pageWidth="595" pageHeight="842" columnWidth="555" leftMargin="20" rightMargin="20" topMargin="20" bottomMargin="20" uuid="320dd56b-4281-455e-becf-bdbfae77892b">
	<property name="ireport.zoom" value="1.0"/>
	<property name="ireport.x" value="0"/>
	<property name="ireport.y" value="0"/>
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
	<parameter name="idCliente" class="java.lang.Integer"/>
	<parameter name="parcela" class="java.lang.String"/>
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
	<variable name="totalEntrada" class="java.lang.Float" calculation="Sum">
		<variableExpression><![CDATA[$F{valorEntrada}]]></variableExpression>
	</variable>
	<variable name="totalSaida" class="java.lang.Float" calculation="Sum">
		<variableExpression><![CDATA[$F{valorSaida}]]></variableExpression>
	</variable>
	<variable name="saldo" class="java.lang.Float"/>
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
				<textFieldExpression><![CDATA["Relatório de Vendas por Recebimento"]]></textFieldExpression>
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
			<staticText>
				<reportElement style="Column header" x="6" y="3" width="84" height="15" uuid="20675a62-e27f-43d2-952c-43166abf1d2d"/>
				<text><![CDATA[Data da Venda]]></text>
			</staticText>
			<staticText>
				<reportElement style="Column header" x="100" y="3" width="47" height="15" uuid="315b305d-476b-41fd-9c5d-af8c628b570f"/>
				<text><![CDATA[Venda]]></text>
			</staticText>
			<staticText>
				<reportElement style="Column header" x="229" y="5" width="93" height="15" uuid="ca145b00-b653-4579-8cb8-36d22ce41438"/>
				<text><![CDATA[Líquido Vendas]]></text>
			</staticText>
			<staticText>
				<reportElement style="Column header" x="152" y="5" width="70" height="15" uuid="75035416-8f5c-4205-a2a4-beb512f1a981"/>
				<text><![CDATA[Valor Bruto]]></text>
			</staticText>
			<staticText>
				<reportElement style="Column header" x="324" y="5" width="79" height="15" uuid="c636b7a4-e5f5-44ea-b789-31d646af217a"/>
				<text><![CDATA[Recebimento]]></text>
			</staticText>
			<staticText>
				<reportElement style="Column header" x="409" y="3" width="92" height="17" uuid="2af0c07a-e85f-474a-9267-704f347555f3"/>
				<text><![CDATA[Valor a Receber]]></text>
			</staticText>
			<staticText>
				<reportElement style="Column header" x="506" y="3" width="50" height="15" uuid="0343894b-83e1-4167-bf69-5f6ddcbbd6d7"/>
				<text><![CDATA[Parcela]]></text>
			</staticText>
		</band>
	</columnHeader>
	<detail>
		<band height="25" splitType="Stretch">
			<rectangle>
				<reportElement x="0" y="0" width="555" height="17" uuid="ee86d585-c7cb-4176-b6d0-71932d13f089"/>
			</rectangle>
			<textField pattern="dd/MM/yyyy">
				<reportElement style="Detail" x="6" y="2" width="67" height="15" uuid="4167ced3-989a-461d-94be-9bb6e2543f83"/>
				<textElement textAlignment="Left">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{dataVenda}]]></textFieldExpression>
			</textField>
			<textField>
				<reportElement style="Detail" x="92" y="2" width="55" height="15" uuid="e0df543e-f9a3-4983-8b5f-111cc992c08b"/>
				<textElement textAlignment="Left">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{idvendas}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00">
				<reportElement style="Detail" x="243" y="0" width="69" height="15" uuid="4c0b5781-b7bb-4213-a971-00ea0f01211d"/>
				<textElement textAlignment="Right">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{liquidoVendas}]]></textFieldExpression>
			</textField>
			<textField pattern="">
				<reportElement style="Detail" x="157" y="0" width="72" height="15" uuid="292c73a8-45fe-48d9-b46b-e85bf4b09d50"/>
				<textElement textAlignment="Left">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{valorBruto}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00" isBlankWhenNull="false">
				<reportElement style="Detail" x="434" y="0" width="51" height="15" uuid="4d77b402-de9a-4751-8969-4aa056573073"/>
				<textElement textAlignment="Right">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{valorParcela}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00">
				<reportElement style="Detail" x="334" y="0" width="69" height="15" uuid="d4edab43-c0ec-430f-8014-e493b1ff6bcc"/>
				<textElement textAlignment="Right">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{dataVencimento}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00">
				<reportElement style="Detail" x="512" y="0" width="25" height="15" uuid="b97ab95f-cec6-4b53-b2d1-1e9d72a1985d"/>
				<textElement textAlignment="Right">
					<font size="10"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{numeroParcela}]]></textFieldExpression>
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
